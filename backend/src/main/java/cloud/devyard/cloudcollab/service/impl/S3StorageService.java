package cloud.devyard.cloudcollab.service.impl;

import cloud.devyard.cloudcollab.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "aws.s3.enabled", havingValue = "true")
public class S3StorageService implements StorageService {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.cloudfront.domain}")
    private String cloudfrontDomain;

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
