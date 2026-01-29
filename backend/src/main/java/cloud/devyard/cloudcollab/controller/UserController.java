package cloud.devyard.cloudcollab.controller;

import cloud.devyard.cloudcollab.dto.ApiResponse;
import cloud.devyard.cloudcollab.dto.request.ChangePasswordRequest;
import cloud.devyard.cloudcollab.dto.request.InviteUserRequest;
import cloud.devyard.cloudcollab.dto.request.UpdateProfileRequest;
import cloud.devyard.cloudcollab.dto.request.UserPreferencesRequest;
import cloud.devyard.cloudcollab.dto.response.InvitationResponse;
import cloud.devyard.cloudcollab.dto.response.Status;
import cloud.devyard.cloudcollab.dto.response.UserDetailResponse;
import cloud.devyard.cloudcollab.dto.response.UserPreferencesResponse;
import cloud.devyard.cloudcollab.exception.BadRequestException;
import cloud.devyard.cloudcollab.security.UserPrincipal;
import cloud.devyard.cloudcollab.service.UserInvitationService;
import cloud.devyard.cloudcollab.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserInvitationService invitationService;

    @GetMapping("/me")
    public ResponseEntity<@NonNull ApiResponse<UserDetailResponse>> getCurrentUser(@AuthenticationPrincipal UserPrincipal currentUser){

        UserDetailResponse user = userService.getUserProfile(currentUser.getId());
        var response = ApiResponse.<UserDetailResponse>builder()
                .status(Status.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .message("Fetch user details successfully")
                .data(user)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<@NonNull ApiResponse<UserDetailResponse>> getUserById(
            @PathVariable Long userId) {

        UserDetailResponse user = userService.getUserProfile(userId);

        var response = ApiResponse.<UserDetailResponse>builder()
                .status(Status.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .message("Fetched user details successfully")
                .data(user)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/me")
    public ResponseEntity<@NonNull ApiResponse<UserDetailResponse>> updateProfile(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody UpdateProfileRequest request,
            HttpServletRequest httpRequest
            ){
        UserDetailResponse updatedUser =  userService.updateProfile(currentUser.getId() , request , httpRequest);
        var response = ApiResponse.<UserDetailResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .status(Status.SUCCESS)
                .message("User details update successfully.")
                .data(updatedUser)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/me/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<@NonNull ApiResponse<Map<String, String>>> uploadAvatar(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest httpRequest) {

        if (file.isEmpty()) {
            throw new BadRequestException("File is empty");
        }

        String avatarUrl = userService.uploadAvatar(currentUser.getId(), file, httpRequest);

        var response = ApiResponse.<Map<String, String>>builder()
                .statusCode(HttpStatus.OK.value())
                .status(Status.SUCCESS)
                .message("Avatar uploaded successfully.")
                .data(Map.of("avatarUrl", avatarUrl))
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/me/password")
    public ResponseEntity<@NonNull ApiResponse<Void>> changePassword(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody ChangePasswordRequest request,
            HttpServletRequest httpRequest) {

        userService.changePassword(currentUser.getId(), request, httpRequest);

        var response = ApiResponse.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .status(Status.SUCCESS)
                .message("Password changed successfully")
                .data(null)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me/preferences")
    public ResponseEntity<@NonNull ApiResponse<UserPreferencesResponse>> getPreferences(@AuthenticationPrincipal UserPrincipal currentUser) {
        UserPreferencesResponse preferences = userService.getPreferences(currentUser.getId());
        var response = ApiResponse.<UserPreferencesResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .status(Status.SUCCESS)
                .message("Fetch user preferences successfully.")
                .data(preferences)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/me/preferences")
    public ResponseEntity<@NonNull ApiResponse<UserPreferencesResponse>> updatePreferences(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestBody UserPreferencesRequest request) {
        UserPreferencesResponse updated = userService.updatePreferences(currentUser.getId(), request);
        var response = ApiResponse.<UserPreferencesResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .status(Status.SUCCESS)
                .message("Update user preferences successfully.")
                .data(updated)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/invite")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<@NonNull ApiResponse<InvitationResponse>> inviteUser(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody InviteUserRequest request) {
        InvitationResponse invitation = invitationService.inviteUser(
                request, currentUser.getOrganizationId(), currentUser.getId());

        var response = ApiResponse.<InvitationResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .status(Status.SUCCESS)
                .message("Fetch Invitation successfully.")
                .data(invitation)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/invitations")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<@NonNull ApiResponse<List<InvitationResponse>>> getInvitations(
            @AuthenticationPrincipal UserPrincipal currentUser) {
        List<InvitationResponse> invitations = invitationService.getOrganizationInvitations(
                currentUser.getOrganizationId());

        var response = ApiResponse.<List<InvitationResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .status(Status.SUCCESS)
                .message("Fetch Invitation successfully.")
                .data(invitations)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/invitations/{invitationId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<@NonNull ApiResponse<Void>> cancelInvitation(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable Long invitationId) {
        invitationService.cancelInvitation(invitationId, currentUser.getId());

        var response = ApiResponse.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .status(Status.SUCCESS)
                .message("Invitation cancelled")
                .data(null)
                .build();

        return ResponseEntity.ok(response);
    }

}
