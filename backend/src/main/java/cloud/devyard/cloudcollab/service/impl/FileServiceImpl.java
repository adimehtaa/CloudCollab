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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
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
        createFilePermission(saveFile , user , PermissionType.OWNER, user);

        userActivityService.logActivity(user, ActivityType.FILE_UPLOAD, "Uploaded file: " + file.getOriginalFilename(), httpRequest);

        return mapToResponse(saveFile);
    }

    @Override
    public Page<FileResponse> getFiles(Long organizationId, Long folderId, Pageable pageable) {
        return null;
    }

    @Override
    public FileResponse getFileById(Long fileId, Long userId) {
        return null;
    }

    @Override
    public FileResponse getFileByShareToken(String shareToken) {
        return null;
    }

    @Override
    public FileResponse updateFile(Long fileId, UpdateFileRequest request, Long userId) {
        return null;
    }

    @Override
    public void deleteFile(Long fileId, Long userId, HttpServletRequest httpRequest) {

    }

    @Override
    public InputStream downloadFile(Long fileId, Long userId) {
        return null;
    }

    @Override
    public String generateDownloadUrl(Long fileId, Long userId, int expirationMinutes) {
        return "";
    }

    @Override
    public Page<FileResponse> searchFiles(Long organizationId, String query, Pageable pageable) {
        return null;
    }

    @Override
    public FileResponse createNewVersion(Long fileId, MultipartFile newFile, Long userId, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public List<FileResponse> getFileVersions(Long fileId, Long userId) {
        return List.of();
    }

    @Override
    public StorageStatsResponse getStorageStats(Long organizationId) {
        return null;
    }

    @Override
    public void shareFile(Long fileId, Long targetUserId, PermissionType permissionType, Long currentUserId) {

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

    private void createFilePermission(File file , User user, PermissionType permissionType, User grantedBy){
        FilePermission filePermission = FilePermission.builder()
                .file(file)
                .user(user)
                .permissionType(permissionType)
                .grantedBy(grantedBy)
                .build();
        filePermissionRepository.save(filePermission);
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
