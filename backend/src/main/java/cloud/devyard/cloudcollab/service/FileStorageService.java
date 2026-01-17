package cloud.devyard.cloudcollab.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    public String uploadAvatar(MultipartFile file, Long userId);
    public void deleteFile(String fileUrl);
}
