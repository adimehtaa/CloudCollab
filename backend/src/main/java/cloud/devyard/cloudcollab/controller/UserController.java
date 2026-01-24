package cloud.devyard.cloudcollab.controller;

import cloud.devyard.cloudcollab.dto.ApiResponse;
import cloud.devyard.cloudcollab.dto.response.Status;
import cloud.devyard.cloudcollab.dto.response.UserDetailResponse;
import cloud.devyard.cloudcollab.security.UserPrincipal;
import cloud.devyard.cloudcollab.service.UserInvitationService;
import cloud.devyard.cloudcollab.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                .message("fetch User details successfully")
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

}
