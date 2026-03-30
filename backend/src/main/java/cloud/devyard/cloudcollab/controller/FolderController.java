package cloud.devyard.cloudcollab.controller;

import cloud.devyard.cloudcollab.dto.ApiResponse;
import cloud.devyard.cloudcollab.dto.request.CreateFolderRequest;
import cloud.devyard.cloudcollab.dto.response.FolderResponse;
import cloud.devyard.cloudcollab.dto.response.Status;
import cloud.devyard.cloudcollab.security.UserPrincipal;
import cloud.devyard.cloudcollab.service.FolderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@NullMarked
@RestController
@RequestMapping("/api/folders")
@RequiredArgsConstructor
public class FolderController {
    private final FolderService folderService;

    @PostMapping
    public ResponseEntity<ApiResponse<FolderResponse>> createFolder(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody CreateFolderRequest request
    ){

        FolderResponse folder = folderService.createFolder(request, currentUser.getId(), currentUser.getOrganizationId());
        var response = ApiResponse.<FolderResponse>builder()
                .status(Status.SUCCESS)
                .statusCode(HttpStatus.CREATED.value())
                .data(folder)
                .message("Folder successfully created.")
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FolderResponse>>> getRootFolders(
            @AuthenticationPrincipal UserPrincipal currentUser
    ){
        List<FolderResponse> folders =  folderService.getRootFolders(currentUser.getOrganizationId());
        var response = ApiResponse.<List<FolderResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .status(Status.SUCCESS)
                .message("Root folders retrieved successfully.")
                .data(folders)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{folderId}")
    public ResponseEntity<ApiResponse<FolderResponse>> getFolderById(
            @PathVariable Long folderId
    ) {

        FolderResponse folder = folderService.getFolderById(
                folderId
        );

        var response = ApiResponse.<FolderResponse>builder()
                .status(Status.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .message("Folder retrieved successfully.")
                .data(folder)
                .build();

        return ResponseEntity.ok(response);
    }


}
