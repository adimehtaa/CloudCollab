package cloud.devyard.cloudcollab.service;

import cloud.devyard.cloudcollab.dto.request.LoginRequest;
import cloud.devyard.cloudcollab.dto.request.SignupRequest;
import cloud.devyard.cloudcollab.dto.response.JwtResponse;
import cloud.devyard.cloudcollab.dto.response.UserResponse;

public interface AuthService {
    public JwtResponse login(LoginRequest loginRequest);
    public UserResponse signup(SignupRequest signupRequest);
}
