package cloud.devyard.cloudcollab.controller;

import cloud.devyard.cloudcollab.dto.ApiResponse;
import cloud.devyard.cloudcollab.dto.request.LoginRequest;
import cloud.devyard.cloudcollab.dto.request.SignupRequest;
import cloud.devyard.cloudcollab.dto.response.JwtResponse;
import cloud.devyard.cloudcollab.dto.response.Status;
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

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> login(@Valid @RequestBody LoginRequest loginRequest){
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
    public ResponseEntity<ApiResponse<UserResponse>> signup(@Valid @RequestBody SignupRequest signupRequest) {
        UserResponse userResponse = authService.signup(signupRequest);
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .status(Status.SUCCESS)
                .message("User register successfully")
                .statusCode(HttpStatus.CREATED.value())
                .data(userResponse)
                .build();

        return ResponseEntity.ok(response);
    }

}
