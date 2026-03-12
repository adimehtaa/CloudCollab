package cloud.devyard.cloudcollab.service.impl;

import cloud.devyard.cloudcollab.dto.request.FileUploadRequest;
import cloud.devyard.cloudcollab.dto.request.UpdateFileRequest;
import cloud.devyard.cloudcollab.dto.response.FileResponse;
import cloud.devyard.cloudcollab.dto.response.StorageStatsResponse;
import cloud.devyard.cloudcollab.exception.BadRequestException;
import cloud.devyard.cloudcollab.exception.ResourceNotFoundException;
import cloud.devyard.cloudcollab.model.*;
import cloud.devyard.cloudcollab.model.enums.ActivityType;
import cloud.devyard.cloudcollab.model.enums.FileStatus;
import cloud.devyard.cloudcollab.model.enums.PermissionType;
import cloud.devyard.cloudcollab.repository.FilePermissionRepository;
import cloud.devyard.cloudcollab.repository.FileRepository;
import cloud.devyard.cloudcollab.repository.FolderRepository;
import cloud.devyard.cloudcollab.repository.UserRepository;
import cloud.devyard.cloudcollab.service.FileService;
import cloud.devyard.cloudcollab.service.OrganizationService;
import cloud.devyard.cloudcollab.service.StorageService;
import cloud.devyard.cloudcollab.service.UserActivityService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private FileRepository fileRepository;

    private FolderRepository folderRepository;

    private UserRepository userRepository;

    private FilePermissionRepository filePermissionRepository;

    private StorageService storageService;

    private UserActivityService userActivityService;

    private OrganizationService organizationService;

    @Value("${storage.max-file-size}")
    private long maxFileSize;

    @Value("${storage.allowed-extensions}")
    private String allowedExtensions;

    @Value("${quota.free-plan}")
    private long freeQuota;

    @Value("${quota.pro-plan}")
    private long proQuota;

    @Value("${quota.business-plan}")
    private long businessQuota;


    @Override
    public FileResponse uploadFile(MultipartFile file, FileUploadRequest request, Long userId, Long organizationId, HttpServletRequest httpRequest) {

        validateFile(file);
        checkStorageQuota(organizationId,file.getSize());

        User user = userRepository.findById(userId).orElseThrow(()-> new UsernameNotFoundException("User not found"));

        Organization organization = organizationService.getOrganizationById(organizationId);

        Folder folder = null;
        if (request.getFolderId() != null){
            folder = folderRepository.findById(request.getFolderId()).orElseThrow(() -> new ResourceNotFoundException("Folder not found"));
        }

        // Upload to storage
        String storageKey = storageService.uploadFile(file,
                folder != null ? "folder-" + folder.getId() : "root",
                organizationId);

        boolean isPublic = Boolean.TRUE.equals(request.getIsPublic());

        File fileEntry = File.builder()
                .name(file.getOriginalFilename())
                .description(request.getDescription())
                .storageKey(storageKey)
                .mimeType(file.getContentType())
                .size(file.getSize())
                .fileExtension(getFileExtension(
                        Objects.requireNonNull(file.getOriginalFilename())))
                .uploadedBy(user)
                .organization(organization)
                .folder(folder)
                .status(FileStatus.ACTIVE)
                .isPublic(isPublic)
                .shareToken(isPublic ? UUID.randomUUID().toString() : null)
                .build();

        File saveFile = fileRepository.save(fileEntry);

        // Create owner permission
        createFilePermission(saveFile , PermissionType.OWNER , user , user);

        userActivityService.logActivity(user, ActivityType.FILE_UPLOAD, "Uploaded file: " + file.getOriginalFilename(), httpRequest);

        return mapToResponse(saveFile);
    }

    @Override
    public Page<@NonNull FileResponse> getFiles(Long organizationId, Long folderId, Pageable pageable) {
        Page<@NonNull File> files;
        if (folderId == null) {
            files = fileRepository.findByOrganizationIdAndStatusAndFolderIsNull(
                    organizationId, FileStatus.ACTIVE, pageable);
        } else {
            files = fileRepository.findByOrganizationIdAndStatusAndFolderId(
                    organizationId, FileStatus.ACTIVE, folderId, pageable);
        }

        return files.map(this::mapToResponse);
    }

    @Override
    public FileResponse getFileById(Long fileId, Long userId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found"));

        // Check permission
        if (!hasPermission(file, userId, PermissionType.VIEW)) {
            throw new BadRequestException("You don't have permission to view this file");
        }

        return mapToResponse(file);
    }

    public FileResponse getFileByShareToken(String shareToken) {
        File file = fileRepository.findByShareToken(shareToken)
                .orElseThrow(() -> new ResourceNotFoundException("File not found"));

        if (!file.getIsPublic()) {
            throw new BadRequestException("This file is not publicly shared");
        }

        return mapToResponse(file);
    }

    @Transactional
    public FileResponse updateFile(Long fileId, UpdateFileRequest request, Long userId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found"));

        if (!hasPermission(file, userId, PermissionType.EDIT)) {
            throw new BadRequestException("You don't have permission to edit this file");
        }

        if (request.getName() != null) file.setName(request.getName());
        if (request.getDescription() != null) file.setDescription(request.getDescription());
        if (request.getFolderId() != null) {
            Folder folder = folderRepository.findById(request.getFolderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Folder not found"));
            file.setFolder(folder);
        }

        File updated = fileRepository.save(file);
        return mapToResponse(updated);
    }

    @Override
    public void deleteFile(Long fileId, Long userId, HttpServletRequest httpRequest) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(()-> new ResourceNotFoundException("File not found"));

        if(!hasPermission(file, userId, PermissionType.DELETE)){
            throw new BadRequestException("You don't have permission to delete this file");
        }

        //soft delete
        file.setStatus(FileStatus.DELETED);
        file.setDeletedAt(LocalDateTime.now());
        fileRepository.save(file);

        // Note: For hard delete, uncomment:
        storageService.deleteFile(file.getStorageKey());
        fileRepository.delete(file);

        User user = userRepository.findById(userId).orElseThrow();
        userActivityService.logActivity(user,ActivityType.FILE_DELETE, "Deleted file: " + file.getName(),httpRequest);
    }

    @Override
    public InputStream downloadFile(Long fileId, Long userId) {
        File file = fileRepository.findById(fileId).orElseThrow(()-> new ResourceNotFoundException("File not found."));

        if(!hasPermission(file, userId, PermissionType.VIEW)){
            throw new BadRequestException("You don't have permission to download this file");
        }
        return storageService.downloadFile(file.getStorageKey());
    }

    @Override
    public String generateDownloadUrl(Long fileId, Long userId, int expirationMinutes) {
        File file = fileRepository.findById(fileId).orElseThrow(()-> new ResourceNotFoundException("File not Found"));

        if (!hasPermission(file,userId, PermissionType.VIEW)){
            throw new BadRequestException("You don't have permission to access this file");
        }
        return storageService.generatePresignedUrl(file.getStorageKey(),expirationMinutes);
    }

    @Override
    public Page<FileResponse> searchFiles(Long organizationId, String query, Pageable pageable) {
        return fileRepository.searchFiles(organizationId, FileStatus.ACTIVE, query, pageable).map(this::mapToResponse);
    }

    @Override
    public FileResponse createNewVersion(Long fileId, MultipartFile newFile, Long userId, HttpServletRequest httpRequest) {
        File originalFile = fileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found"));

        if (!hasPermission(originalFile, userId, PermissionType.EDIT)) {
            throw new BadRequestException("You don't have permission to create a new version");
        }

        validateFile(newFile);
        checkStorageQuota(originalFile.getOrganization().getId(), newFile.getSize());

        User user = userRepository.findById(userId).orElseThrow();

        // Upload new version
        String storageKey = storageService.uploadFile(newFile,
                "versions", originalFile.getOrganization().getId());

        // Create new version
        File newVersion = File.builder()
                .name(originalFile.getName())
                .description("Version " + (originalFile.getVersion() + 1))
                .storageKey(storageKey)
                .mimeType(newFile.getContentType())
                .size(newFile.getSize())
                .fileExtension(getFileExtension(Objects.requireNonNull(newFile.getOriginalFilename())))
                .uploadedBy(user)
                .organization(originalFile.getOrganization())
                .folder(originalFile.getFolder())
                .status(FileStatus.ACTIVE)
                .parentFile(originalFile)
                .version(originalFile.getVersion() + 1)
                .build();

        File savedVersion = fileRepository.save(newVersion);

        userActivityService.logActivity(user, ActivityType.FILE_UPLOAD,
                "Created new version of file: " + originalFile.getName(), httpRequest);

        return mapToResponse(savedVersion);
    }

    @Override
    public List<FileResponse> getFileVersions(Long fileId, Long userId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found"));

        if (!hasPermission(file, userId, PermissionType.VIEW)) {
            throw new BadRequestException("You don't have permission to view versions");
        }

        // Get all versions of the root file
        File rootFile = file.getParentFile() != null ? file.getParentFile() : file;
        List<File> versions = fileRepository.findByParentFileIdOrderByVersionDesc(rootFile.getId());

        // Include root file
        versions.addFirst(rootFile);

        return versions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public StorageStatsResponse getStorageStats(Long organizationId) {
        Long usedStorage = fileRepository.getTotalStorageUsed(organizationId, FileStatus.ACTIVE);
        if(usedStorage == null) usedStorage = 0L;

        Organization organization = organizationService.getOrganizationById(organizationId);
        Long totalQuota = getQuotaForPlan(organization.getSubscriptionPlan());

        return StorageStatsResponse.builder()
                .usedStorage(usedStorage)
                .totalQuota(totalQuota)
                .availableStorage(totalQuota - usedStorage)
                .usagePercentage((usedStorage * 100.0 / totalQuota))
                .build();
    }

    @Override
    public void shareFile(Long fileId, Long targetUserId, PermissionType permissionType, Long currentUserId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(()-> new ResourceNotFoundException("File not Found."));

        if (!hasPermission(file, currentUserId, PermissionType.SHARE)){
            throw new BadRequestException("You don't have permission to share this file");
        }

        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Shared User not found"));

        User currentUser = userRepository.findById(currentUserId).orElseThrow();

        createFilePermission(file , permissionType ,targetUser , currentUser);
    }

    @Override
    public void revokeFileAccess(Long fileId, Long targetUserId, Long currentUserId) {

    }

    // Helper methods
    private void validateFile(MultipartFile file){
        if(file.isEmpty()){
            throw new BadRequestException("File is missing");
        }

        if(file.getSize() > maxFileSize){
            throw new BadRequestException("File size exceeds maximum limit of " +
                    (maxFileSize / 1024 / 1024) + "MB");
        }

        String extension = getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
        List<String> allowed = Arrays.asList(allowedExtensions.split(","));
        if (!allowed.contains(extension.toLowerCase())){
            throw new BadRequestException("File type not allowed. Allowed types:" + allowedExtensions);
        }
    }

    private String getFileExtension(String filename){
        int dotIndex = filename.lastIndexOf(".");
        return dotIndex > 0 ? filename.substring(dotIndex + 1) : "";
    }

    public void checkStorageQuota(Long organizationId, long filesize){
        Organization org = organizationService.getOrganizationById(organizationId);
        Long userStorage = fileRepository.getTotalStorageUsed(organizationId, FileStatus.ACTIVE);

        if (userStorage == null) userStorage = 0L;

        Long quota = getQuotaForPlan(org.getSubscriptionPlan());

        if(userStorage + filesize > quota){
            throw new BadRequestException("Storage quota exceeded. Please upgrade your plan.");
        }
    }

    private Long getQuotaForPlan(String plan){
        return switch (plan.toUpperCase()){
            case "PRO" -> proQuota;
            case "BUSINESS", "ENTERPRISE" -> businessQuota;
            default -> freeQuota;
        };
    }

    private void createFilePermission(File file ,PermissionType permissionType ,User user, User grantedBy){
        FilePermission filePermission = FilePermission.builder()
                .file(file)
                .user(user)
                .permissionType(permissionType)
                .grantedBy(grantedBy)
                .build();
        filePermissionRepository.save(filePermission);
    }

    private boolean hasPermission(File file, Long userId, PermissionType requiredPermission) {

        // Owner always has all permissions
        if (file.getUploadedBy().getId().equals(userId)){
            return true;
        }

        // Check explicit permissions
        Optional<FilePermission> permission = filePermissionRepository.findByFileIdAndUserId(
                file.getId(), userId);

        if (permission.isEmpty()) {
            return false;
        }

        PermissionType userPermission = permission.get().getPermissionType();

        // Permission hierarchy: OWNER > DELETE > EDIT > SHARE > VIEW
        return switch (requiredPermission) {
            case VIEW -> true; // All permissions include VIEW
            case SHARE -> userPermission == PermissionType.SHARE ||
                    userPermission == PermissionType.EDIT ||
                    userPermission == PermissionType.DELETE ||
                    userPermission == PermissionType.OWNER;
            case EDIT -> userPermission == PermissionType.EDIT ||
                    userPermission == PermissionType.DELETE ||
                    userPermission == PermissionType.OWNER;
            case DELETE -> userPermission == PermissionType.DELETE ||
                    userPermission == PermissionType.OWNER;
            case OWNER -> userPermission == PermissionType.OWNER;
        };
    }

    private FileResponse mapToResponse(File file) {

        return FileResponse.builder()
                .id(file.getId())
                .name(file.getName())
                .description(file.getDescription())
                .mimeType(file.getMimeType())
                .size(file.getSize())
                .fileExtension(file.getFileExtension())
                .version(file.getVersion())
                .isPublic(file.getIsPublic())
                .shareToken(file.getShareToken())
                .uploadedById(file.getUploadedBy().getId())
                .uploadedByName(
                        file.getUploadedBy().getFirstName() + " " +
                                file.getUploadedBy().getLastName()
                )
                .folderId(file.getFolder() != null ? file.getFolder().getId() : null)
                .folderName(file.getFolder() != null ? file.getFolder().getName() : null)
                .uploadedAt(file.getUploadedAt())
                .updatedAt(file.getUpdatedAt())

                // Generate download URL (15 minutes expiry)
                .downloadUrl(
                        storageService.generatePresignedUrl(
                                file.getStorageKey(), 15
                        )
                )
                .build();
    }
}
