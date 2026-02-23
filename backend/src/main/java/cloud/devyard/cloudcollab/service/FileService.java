package cloud.devyard.cloudcollab.service;

import cloud.devyard.cloudcollab.dto.request.FileUploadRequest;
import cloud.devyard.cloudcollab.dto.request.UpdateFileRequest;
import cloud.devyard.cloudcollab.dto.response.FileResponse;
import cloud.devyard.cloudcollab.dto.response.StorageStatsResponse;
import cloud.devyard.cloudcollab.model.enums.PermissionType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface FileService {

    public FileResponse uploadFile(MultipartFile file, FileUploadRequest request,
                                   Long userId, Long organizationId, HttpServletRequest httpRequest);
    public Page<FileResponse> getFiles(Long organizationId, Long folderId, Pageable pageable);
    public FileResponse getFileById(Long fileId, Long userId);
    public FileResponse getFileByShareToken(String shareToken);
    public FileResponse updateFile(Long fileId, UpdateFileRequest request, Long userId);
    public void deleteFile(Long fileId, Long userId, HttpServletRequest httpRequest);
    public InputStream downloadFile(Long fileId, Long userId);
    public String generateDownloadUrl(Long fileId, Long userId, int expirationMinutes);
    public Page<FileResponse> searchFiles(Long organizationId, String query, Pageable pageable);
    public FileResponse createNewVersion(Long fileId, MultipartFile newFile,
                                         Long userId, HttpServletRequest httpRequest);
    public List<FileResponse> getFileVersions(Long fileId, Long userId);
    public StorageStatsResponse getStorageStats(Long organizationId);
    public void shareFile(Long fileId, Long targetUserId, PermissionType permissionType, Long currentUserId);
    public void revokeFileAccess(Long fileId, Long targetUserId, Long currentUserId);

}
