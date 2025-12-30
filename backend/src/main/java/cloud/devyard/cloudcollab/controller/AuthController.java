package cloud.devyard.cloudcollab.controller;

import cloud.devyard.cloudcollab.dto.ApiResponse;
import cloud.devyard.cloudcollab.dto.request.SignupRequest;
import cloud.devyard.cloudcollab.dto.response.JwtResponse;
import cloud.devyard.cloudcollab.dto.response.UserResponse;
import cloud.devyard.cloudcollab.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserResponse>> signup(@Valid @RequestBody SignupRequest signupRequest) {
        UserResponse userResponse = authService.signup(signupRequest);
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .code(HttpStatus.ACCEPTED.value())
                .data(userResponse)
                .message("User register successfully")
                .build();

        return ResponseEntity.ok(response);
    }

}
