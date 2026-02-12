package cloud.devyard.cloudcollab.service.impl;

import cloud.devyard.cloudcollab.service.StorageService;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public class S3StorageService implements StorageService {
    @Override
    public String uploadFile(MultipartFile file, String folder, Long organizationId) {
        return "";
    }

    @Override
    public InputStream downloadFile(String key) {
        return null;
    }

    @Override
    public void deleteFile(String key) {

    }

    @Override
    public String generatePresignedUrl(String key, int expirationMinutes) {
        return "";
    }

    @Override
    public boolean fileExists(String key) {
        return false;
    }
}
