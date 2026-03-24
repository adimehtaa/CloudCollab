package cloud.devyard.cloudcollab.controller;

import cloud.devyard.cloudcollab.dto.ApiResponse;
import cloud.devyard.cloudcollab.dto.request.FileUploadRequest;
import cloud.devyard.cloudcollab.dto.response.FileResponse;
import cloud.devyard.cloudcollab.dto.response.Status;
import cloud.devyard.cloudcollab.security.UserPrincipal;
import cloud.devyard.cloudcollab.service.FileService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
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

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<@NonNull ApiResponse<FileResponse>> uploadFile(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Long folderId,
            @RequestParam(required = false) Boolean isPublic,
            HttpServletRequest httpRequest
    ){

        FileUploadRequest request = FileUploadRequest.builder()
                .description(description)
                .folderId(folderId)
                .isPublic(isPublic)
                .build();

        FileResponse fileResponse = fileService.uploadFile(
                file,
                request,
                currentUser.getId(),
                currentUser.getOrganizationId(),
                httpRequest);

        var response = ApiResponse.<FileResponse>builder()
                .status(Status.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .message("Upload file successfully.")
                .data(fileResponse)
                .build();

        return ResponseEntity.ok(response);

    }

    @GetMapping
    public ResponseEntity<@NonNull ApiResponse<Page<@NonNull FileResponse>>> getFiles(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam(required = false) Long folderId,
            Pageable pageable
    ){
        Page<@NonNull FileResponse> files = fileService.getFiles(currentUser.getOrganizationId(), folderId, pageable);

        var response = ApiResponse.<Page<@NonNull FileResponse>>builder()
                .status(Status.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .message("Fetch files successfully.")
                .data(files)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{fileId}")
    public ResponseEntity<@NonNull ApiResponse<FileResponse>> getFileById(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long fileId
    ){
        FileResponse file = fileService.getFileById(fileId, currentUser.getId());

        var response = ApiResponse.<FileResponse>builder()
                .status(Status.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .message("Fetch file successfully.")
                .data(file)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/shared/{shareToken}")
    public ResponseEntity<FileResponse> getFileByShareToken(@PathVariable String shareToken) {
        FileResponse file = fileService.getFileByShareToken(shareToken);
        return ResponseEntity.ok(file);
    }

    @PutMapping("/{fileId}")
    public ResponseEntity<FileResponse> updateFile(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long fileId,
            @RequestBody FileUploadRequest request) {
        FileResponse updated = fileService.updateFile(fileId, request, currentUser.getId());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<ApiResponse> deleteFile(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long fileId,
            HttpServletRequest httpRequest) {
        fileService.deleteFile(fileId, currentUser.getId(), httpRequest);
        return ResponseEntity.ok(new ApiResponse(true, "File deleted successfully"));
    }

    @GetMapping("/{fileId}/download")
    public ResponseEntity<InputStreamResource> downloadFile(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long fileId) {

        FileResponse fileInfo = fileService.getFileById(fileId, currentUser.getId());
        InputStream fileStream = fileService.downloadFile(fileId, currentUser.getId());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileInfo.getName() + "\"")
                .contentType(MediaType.parseMediaType(fileInfo.getMimeType()))
                .body(new InputStreamResource(fileStream));
    }

    @GetMapping("/{fileId}/download-url")
    public ResponseEntity<ApiResponse> generateDownloadUrl(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long fileId,
            @RequestParam(defaultValue = "15") int expirationMinutes) {

        String url = fileService.generateDownloadUrl(fileId, currentUser.getId(), expirationMinutes);
        return ResponseEntity.ok(new ApiResponse(true, url));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<FileResponse>> searchFiles(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam String query,
            Pageable pageable) {
        Page<FileResponse> files = fileService.searchFiles(
                currentUser.getOrganizationId(), query, pageable);
        return ResponseEntity.ok(files);
    }

    @PostMapping("/{fileId}/versions")
    public ResponseEntity<FileResponse> createNewVersion(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long fileId,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest httpRequest) {

        FileResponse response = fileService.createNewVersion(fileId, file,
                currentUser.getId(), httpRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{fileId}/versions")
    public ResponseEntity<List<FileResponse>> getFileVersions(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long fileId) {
        List<FileResponse> versions = fileService.getFileVersions(fileId, currentUser.getId());
        return ResponseEntity.ok(versions);
    }

}
