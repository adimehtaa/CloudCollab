package cloud.devyard.cloudcollab.service;

import cloud.devyard.cloudcollab.dto.request.ChangePasswordRequest;
import cloud.devyard.cloudcollab.dto.request.UpdateProfileRequest;
import cloud.devyard.cloudcollab.dto.request.UserPreferencesRequest;
import cloud.devyard.cloudcollab.dto.response.UserDetailResponse;
import cloud.devyard.cloudcollab.dto.response.UserPreferencesResponse;
import cloud.devyard.cloudcollab.model.User;
import cloud.devyard.cloudcollab.model.UserPreferences;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    public UserDetailResponse getUserProfile(Long userId);
    public UserDetailResponse updateProfile(Long userId, UpdateProfileRequest request, HttpServletRequest httpRequest);
    public String uploadAvatar(Long userId, MultipartFile file, HttpServletRequest httpRequest);
    public void changePassword(Long userId, ChangePasswordRequest request, HttpServletRequest httpRequest);
    public UserPreferencesResponse updatePreferences(Long userId, UserPreferencesRequest request);
    public UserPreferencesResponse getPreferences(Long userId);
    public Page<User> searchUsers(String query, Long organizationId, Pageable pageable);
    }
