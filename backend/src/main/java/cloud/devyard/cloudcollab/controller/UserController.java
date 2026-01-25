package cloud.devyard.cloudcollab.controller;

import cloud.devyard.cloudcollab.dto.ApiResponse;
import cloud.devyard.cloudcollab.dto.request.UpdateProfileRequest;
import cloud.devyard.cloudcollab.dto.response.Status;
import cloud.devyard.cloudcollab.dto.response.UserDetailResponse;
import cloud.devyard.cloudcollab.security.UserPrincipal;
import cloud.devyard.cloudcollab.service.UserInvitationService;
import cloud.devyard.cloudcollab.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserInvitationService userInvitationService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDetailResponse>> getCurrentUser(@AuthenticationPrincipal UserPrincipal currentUser){

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
    public ResponseEntity<ApiResponse<UserDetailResponse>> getUserById(
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
    public ResponseEntity<ApiResponse<UserDetailResponse>> updateProfile(
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



}
