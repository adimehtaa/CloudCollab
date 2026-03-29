package cloud.devyard.cloudcollab.controller;

import cloud.devyard.cloudcollab.dto.ApiResponse;
import cloud.devyard.cloudcollab.dto.request.FileUploadRequest;
import cloud.devyard.cloudcollab.dto.request.ShareFileRequest;
import cloud.devyard.cloudcollab.dto.request.UpdateFileRequest;
import cloud.devyard.cloudcollab.dto.response.FileResponse;
import cloud.devyard.cloudcollab.dto.response.Status;
import cloud.devyard.cloudcollab.dto.response.StorageStatsResponse;
import cloud.devyard.cloudcollab.security.UserPrincipal;
import cloud.devyard.cloudcollab.service.FileService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;


@NullMarked
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<FileResponse>> uploadFile(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Long folderId,
            @RequestParam(required = false) Boolean isPublic,
            HttpServletRequest httpRequest
    ) {
        FileUploadRequest request = FileUploadRequest.builder()
                .description(description)
                .folderId(folderId)
                .isPublic(isPublic)
                .build();

        FileResponse fileResponse = fileService.uploadFile(
                file, request, currentUser.getId(), currentUser.getOrganizationId(), httpRequest);

        return ResponseEntity.ok(ApiResponse.<FileResponse>builder()
                .status(Status.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .message("File uploaded successfully.")
                .data(fileResponse)
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<FileResponse>>> getFiles(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam(required = false) Long folderId,
            Pageable pageable
    ) {
        Page<FileResponse> files = fileService.getFiles(currentUser.getOrganizationId(), folderId, pageable);

        return ResponseEntity.ok(ApiResponse.<Page<FileResponse>>builder()
                .status(Status.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .message("Files fetched successfully.")
                .data(files)
                .build());
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<ApiResponse<FileResponse>> getFileById(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long fileId
    ) {
        FileResponse file = fileService.getFileById(fileId, currentUser.getId());

        return ResponseEntity.ok(ApiResponse.<FileResponse>builder()
                .status(Status.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .message("File fetched successfully.")
                .data(file)
                .build());
    }

    @GetMapping("/shared/{shareToken}")
    public ResponseEntity<ApiResponse<FileResponse>> getFileByShareToken(@PathVariable String shareToken) {
        FileResponse file = fileService.getFileByShareToken(shareToken);

        return ResponseEntity.ok(ApiResponse.<FileResponse>builder()
                .status(Status.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .message("File fetched successfully.")
                .data(file)
                .build());
    }

    @PutMapping("/{fileId}")
    public ResponseEntity<ApiResponse<FileResponse>> updateFile(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long fileId,
            @Valid @RequestBody UpdateFileRequest request
    ) {
        FileResponse updated = fileService.updateFile(fileId, request, currentUser.getId());

        return ResponseEntity.ok(ApiResponse.<FileResponse>builder()
                .status(Status.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .message("File updated successfully.")
                .data(updated)
                .build());
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<ApiResponse<Void>> deleteFile(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long fileId,
            HttpServletRequest httpRequest
    ) {
        fileService.deleteFile(fileId, currentUser.getId(), httpRequest);

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status(Status.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .message("File deleted successfully.")
                .build());
    }

    @GetMapping("/{fileId}/download")
    public ResponseEntity<InputStreamResource> downloadFile(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long fileId
    ) {
        FileResponse fileInfo = fileService.getFileById(fileId, currentUser.getId());
        InputStream fileStream = fileService.downloadFile(fileId, currentUser.getId());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileInfo.getName() + "\"")
                .contentType(MediaType.parseMediaType(fileInfo.getMimeType()))
                .body(new InputStreamResource(fileStream));
    }

    @GetMapping("/{fileId}/download-url")
    public ResponseEntity<ApiResponse<String>> generateDownloadUrl(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long fileId,
            @RequestParam(defaultValue = "15") int expirationMinutes
    ) {
        String url = fileService.generateDownloadUrl(fileId, currentUser.getId(), expirationMinutes);

        return ResponseEntity.ok(ApiResponse.<String>builder()
                .status(Status.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .message("Download URL generated successfully.")
                .data(url)
                .build());
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<FileResponse>>> searchFiles(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam String query,
            Pageable pageable
    ) {
        Page<FileResponse> files = fileService.searchFiles(currentUser.getOrganizationId(), query, pageable);

        return ResponseEntity.ok(ApiResponse.<Page<FileResponse>>builder()
                .status(Status.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .message("Files searched successfully.")
                .data(files)
                .build());
    }

    @PostMapping("/{fileId}/versions")
    public ResponseEntity<ApiResponse<FileResponse>> createNewVersion(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long fileId,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest httpRequest
    ) {
        FileResponse fileResponse = fileService.createNewVersion(fileId, file, currentUser.getId(), httpRequest);

        return ResponseEntity.ok(ApiResponse.<FileResponse>builder()
                .status(Status.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .message("New file version created successfully.")
                .data(fileResponse)
                .build());
    }

    @GetMapping("/{fileId}/versions")
    public ResponseEntity<ApiResponse<List<FileResponse>>> getFileVersions(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long fileId
    ) {
        List<FileResponse> versions = fileService.getFileVersions(fileId, currentUser.getId());

        return ResponseEntity.ok(ApiResponse.<List<FileResponse>>builder()
                .status(Status.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .message("File versions fetched successfully.")
                .data(versions)
                .build());
    }

    @PostMapping("/{fileId}/share")
    public ResponseEntity<ApiResponse<Void>> shareFile(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long fileId,
            @Valid @RequestBody ShareFileRequest request
    ) {
        fileService.shareFile(fileId, request.getUserId(), request.getPermissionType(), currentUser.getId());

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status(Status.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .message("File shared successfully.")
                .build());
    }

    @DeleteMapping("/{fileId}/share/{userId}")
    public ResponseEntity<ApiResponse<Void>> revokeFileAccess(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long fileId,
            @PathVariable Long userId
    ) {
        fileService.revokeFileAccess(fileId, userId, currentUser.getId());

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status(Status.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .message("File access revoked successfully.")
                .build());
    }

    @GetMapping("/storage/stats")
    public ResponseEntity<ApiResponse<StorageStatsResponse>> getStorageStats(
            @AuthenticationPrincipal UserPrincipal currentUser
    ){
        StorageStatsResponse stats = fileService.getStorageStats(currentUser.getOrganizationId());
        return ResponseEntity.ok(ApiResponse.<StorageStatsResponse>builder()
                .status(Status.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .message("Storage stats.")
                .data(stats)
                .build());
    }
}