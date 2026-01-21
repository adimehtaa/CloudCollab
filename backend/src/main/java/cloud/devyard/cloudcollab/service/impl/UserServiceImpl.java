package cloud.devyard.cloudcollab.service.impl;


import cloud.devyard.cloudcollab.dto.request.ChangePasswordRequest;
import cloud.devyard.cloudcollab.dto.request.UpdateProfileRequest;
import cloud.devyard.cloudcollab.dto.request.UserPreferencesRequest;
import cloud.devyard.cloudcollab.dto.response.UserDetailResponse;
import cloud.devyard.cloudcollab.model.User;
import cloud.devyard.cloudcollab.model.UserPreferences;
import cloud.devyard.cloudcollab.repository.UserPreferencesRepository;
import cloud.devyard.cloudcollab.repository.UserRepository;
import cloud.devyard.cloudcollab.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserPreferencesRepository userPreferencesRepository;

    @Override
    public UserDetailResponse getUserProfile(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow( () -> new UsernameNotFoundException("User not found"));

        return mapToDetailResponse(user);
    }

    @Override
    public UserDetailResponse updateProfile(Long userId, UpdateProfileRequest request, HttpServletRequest httpRequest) {
        return null;
    }

    @Override
    public String uploadAvatar(Long userId, MultipartFile file, HttpServletRequest httpRequest) {
        return "";
    }

    @Override
    public void changePassword(Long userId, ChangePasswordRequest request, HttpServletRequest httpRequest) {

    }

    @Override
    public UserPreferences updatePreferences(Long userId, UserPreferencesRequest request) {
        return null;
    }

    @Override
    public UserPreferences getPreferences(Long userId) {
        return null;
    }

    @Override
    public Page<User> searchUsers(String query, Long organizationId, Pageable pageable) {
        return null;
    }


    private UserDetailResponse mapToDetailResponse(User user){
        return UserDetailResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .avatarUrl(user.getAvatarUrl())
                .bio(user.getBio())
                .phoneNumber(user.getPhoneNumber())
                .jobTitle(user.getJobTitle())
                .department(user.getDepartment())
                .location(user.getLocation())
                .dateOfBirth(user.getDateOfBirth().toLocalDate())
                .emailVerified(user.getEmailVerified())
                .active(user.getActive())
                .organizationId(user.getOrganization() != null ? user.getOrganization().getId() : null)
                .organizationName(user.getOrganization() != null ? user.getOrganization().getName() : null)
                .roles(user.getRoles().stream().map(role -> role.getName().name()).toList())
                .createdAt(user.getCreatedAt())
                .lastLoginAt(user.getLastLogin())
                .build();
    }

}
