package cloud.devyard.cloudcollab.service.impl;

import cloud.devyard.cloudcollab.exception.BadRequestException;
import cloud.devyard.cloudcollab.service.StorageService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MinioStorageService implements StorageService {

    private static final Logger log = LoggerFactory.getLogger(MinioStorageService.class);
    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Override
    public String uploadFile(MultipartFile file, String folder, Long organizationId) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("File is empty");
        }

        String key = generateKey(file.getOriginalFilename(), folder, organizationId);

        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(key)
                            .contentType(Objects.requireNonNullElse(file.getContentType(), "application/octet-stream"))
                            .stream(inputStream, file.getSize(), -1)
                            .build()
            );

            log.info("File uploaded successfully to MinIO: {}", key);
            return key;

        } catch (ErrorResponseException e) {
            log.error("MinIO error response: {}", e.errorResponse(), e);
            throw new BadRequestException("MinIO rejected the request: " + e.getMessage());
        } catch (IOException e) {
            log.error("File read error", e);
            throw new BadRequestException("Unable to read file");
        } catch (Exception e) {
            log.error("MinIO operation failed", e);
            throw new BadRequestException("Storage service error: " + e.getMessage());
        }
    }

    @Override
    public InputStream downloadFile(String key) {
        try {
            GetObjectArgs args = GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(key)
                    .build();

            return minioClient.getObject(args);
        } catch (Exception e) {  // ← catch broader MinIO exceptions
            log.error("Failed to download file from minio: {}", key, e);
            throw new BadRequestException("Failed to download file: " + e.getMessage());
        }
    }


    @Override
    public void deleteFile(String key) {
        try {
            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(key)
                    .build();

            minioClient.removeObject(removeObjectArgs);
        } catch (Exception e) {
            log.error("Failed to delete file from minio: {}", key, e);
            throw new BadRequestException("Failed to delete file: " + e.getMessage());
        }
    }

    @Override
    public String generatePresignedUrl(String key, int expirationMinutes) {
        try {
            GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(key)
                    .expiry(expirationMinutes, TimeUnit.MINUTES)
                    .build();

            return minioClient.getPresignedObjectUrl(args);
        } catch (Exception e) {
            log.error("Failed to generate presigned URL for: {}", key, e);
            throw new BadRequestException("Failed to generate download URL");
        }
    }

    @Override
    public boolean fileExists(String key) {
        try {
            StatObjectArgs statObjectArgs = StatObjectArgs.builder()
                    .bucket(bucketName)
                    .object(key)
                    .build();

            minioClient.statObject(statObjectArgs);
            return true;
        } catch (ErrorResponseException e) {
            // NoSuchKey / NoSuchObject → object does not exist
            String code = e.errorResponse().code();
            if ("NoSuchKey".equals(code) || "NoSuchObject".equals(code)) {
                return false;
            }
            log.error("Error while checking existence of file '{}': {}", key, code, e);
            throw new RuntimeException("Failed to check file existence: " + code, e);
        } catch (Exception e) {
            log.error("Unexpected error while checking existence of file '{}': {}", key, e);
            throw new BadRequestException("Failed to check file existence");
        }
    }

    private String generateKey(String originalFileName, String folder, Long organizationId){
        String extension = "";
        int dotIndex = originalFileName.lastIndexOf('.');
        extension = dotIndex > 0 ? originalFileName.substring(dotIndex) : "";
        String uniqueId = UUID.randomUUID().toString();
        return String.format("org-%d/%s/%s%s", organizationId , folder , uniqueId,extension);
    }
}
