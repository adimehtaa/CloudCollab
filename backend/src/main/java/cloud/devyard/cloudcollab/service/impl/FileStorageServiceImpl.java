package cloud.devyard.cloudcollab.service.impl;

import cloud.devyard.cloudcollab.exception.BadRequestException;
import cloud.devyard.cloudcollab.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${file.upload.dir:uploads}")
    private String uploadDir;

    @Value("${file.max-size:5242880}") // 5MB default
    private long maxFileSize;

    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList(
            "image/jpeg", "image/png", "image/gif", "image/webp"
    );

    @Override
    public String uploadAvatar(MultipartFile file, Long userId) {

        validateImageFile(file);
        try{

            Path userDir = Paths.get(uploadDir, "avatars", userId.toString());
            Files.createDirectories(userDir);

            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = UUID.randomUUID().toString() + extension;

            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            BufferedImage resizedImage = resizeImage(originalImage , 200 , 200);

            Path targetPath = userDir.resolve(filename);
            File outputFile = targetPath.toFile();
            ImageIO.write(resizedImage, extension.substring(1), outputFile);

            return "/uploads/avatars/" + userId + "/" + filename;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateImageFile(MultipartFile file) {

        if (file.isEmpty()){
            throw new BadRequestException("File is empty");
        }

        if (file.getSize() > maxFileSize){
            throw new BadRequestException("File size exceeds maximum limit of " + (maxFileSize / 1024 / 1024) + "MB");
        }

        String contentType = file.getContentType();
        if (!ALLOWED_IMAGE_TYPES.contains(contentType)) {
            throw new BadRequestException("Invalid file type. Only JPEG, PNG, GIF, and WebP images are allowed");
        }
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight){
        Image resultingImage = originalImage.getScaledInstance(targetWidth ,targetHeight , Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    @Override
    public void deleteFile(String fileUrl) {
        try{
            if (fileUrl != null && fileUrl.startsWith("/uploads/")) {
                Path filePath = Paths.get(uploadDir, fileUrl.replace("/uploads/", ""));
                Files.deleteIfExists(filePath);
            }
        } catch (IOException e) {
            System.err.println("Failed to delete file: " + e.getMessage());
        }
    }
}
