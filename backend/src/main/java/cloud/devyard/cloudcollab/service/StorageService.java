package cloud.devyard.cloudcollab.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface StorageService {

    String uploadFile(MultipartFile file, String folder, Long organizationId);
    InputStream downloadFile(String key);
    void deleteFile(String key);
    String generatePresignedUrl(String key, int expirationMinutes);
    boolean fileExists(String key);
}
