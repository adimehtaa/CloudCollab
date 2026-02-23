package cloud.devyard.cloudcollab.service.impl;

import cloud.devyard.cloudcollab.exception.BadRequestException;
import cloud.devyard.cloudcollab.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
public class LocalStorageService implements StorageService {

    @Value("${file.upload.dir}")
    private String uploadDir;

    @Override
    public String uploadFile(MultipartFile file, String folder, Long organizationId) {
        try{
            Path orgDir = Path.of(uploadDir, "org-" + organizationId, folder);
            Files.createDirectories(orgDir);

            String filename = UUID.randomUUID().toString() + "-" +file.getOriginalFilename();
            Path targetPath = orgDir.resolve(filename);

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            return String.format("org-%d/%s/%s", organizationId, folder, filename);

        } catch (IOException e) {
            log.error("Failed to store file locally", e);
            throw new BadRequestException("Failed to upload file: " + e.getMessage());
        }
    }

    @Override
    public InputStream downloadFile(String key) {
        try {
            Path filePath = Paths.get(uploadDir, key);
            return Files.newInputStream(filePath);
        } catch (IOException e) {
            throw new BadRequestException("Failed to download file: " + e.getMessage());
        }
    }

    @Override
    public void deleteFile(String key) {
        try{
            Path filePath = Paths.get(uploadDir , key);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.error("Failed to delete file: {}", key, e);
        }
    }

    @Override
    public String generatePresignedUrl(String key, int expirationMinutes) {
        return "/uploads/"+ key;
    }

    @Override
    public boolean fileExists(String key) {
        Path filePath = Paths.get(uploadDir, key);
        return Files.exists(filePath);
    }
}