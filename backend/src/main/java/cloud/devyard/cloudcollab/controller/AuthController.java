package cloud.devyard.cloudcollab.controller;

import cloud.devyard.cloudcollab.dto.ApiResponse;
import cloud.devyard.cloudcollab.dto.request.LoginRequest;
import cloud.devyard.cloudcollab.dto.request.RefreshTokenRequest;
import cloud.devyard.cloudcollab.dto.request.SignupRequest;
import cloud.devyard.cloudcollab.dto.response.JwtResponse;
import cloud.devyard.cloudcollab.dto.response.Status;
import cloud.devyard.cloudcollab.dto.response.UserResponse;
import cloud.devyard.cloudcollab.security.UserPrincipal;
import cloud.devyard.cloudcollab.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<@NonNull ApiResponse<JwtResponse>> login(@Valid @RequestBody LoginRequest loginRequest){
        JwtResponse jwtResponse = authService.login(loginRequest);
        var response = ApiResponse.<JwtResponse>builder()
                .status(Status.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .message("User login successfully")
                .data(jwtResponse)
                .build();

        return ResponseEntity.ok(response);
    }


    @PostMapping("/signup")
    public ResponseEntity<@NonNull ApiResponse<UserResponse>> signup(@Valid @RequestBody SignupRequest signupRequest) {
        UserResponse userResponse = authService.signup(signupRequest);
        var response = ApiResponse.<UserResponse>builder()
                .status(Status.SUCCESS)
                .message("User register successfully")
                .statusCode(HttpStatus.CREATED.value())
                .data(userResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<@NonNull ApiResponse<JwtResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
    JwtResponse jwtResponse = authService.refreshToken(request.getRefreshToken());
        var response = ApiResponse.<JwtResponse>builder()
                .status(Status.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .message("User login successfully")
                .data(jwtResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<@NonNull ApiResponse<Void>> logout(@AuthenticationPrincipal UserPrincipal currentUser){
        authService.logout(currentUser.getId());
        var response = ApiResponse.<Void>builder()
                .status(Status.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .message("Logged out successfully")
                .data(null)
                .build();

        return ResponseEntity.ok(response);
    }

}
