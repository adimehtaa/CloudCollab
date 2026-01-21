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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found exception."));

        UserPreferences preferences = userPreferencesRepository.findByUserId(userId)
                .orElseGet(() -> {
                    return UserPreferences.builder()
                            .user(user)
                            .build();
                });

        if (request.getEmailNotifications() != null)
            preferences.setEmailNotifications(request.getEmailNotifications());
        if (request.getPushNotifications() != null)
            preferences.setPushNotifications(request.getPushNotifications());
        if (request.getSmsNotifications() != null)
            preferences.setSmsNotifications(request.getSmsNotifications());
        if (request.getTheme() != null)
            preferences.setTheme(request.getTheme());
        if (request.getLanguage() != null)
            preferences.setLanguage(request.getLanguage());
        if (request.getTimezone() != null)
            preferences.setTimezone(request.getTimezone());
        if (request.getProfilePublic() != null)
            preferences.setProfilePublic(request.getProfilePublic());
        if (request.getShowEmail() != null)
            preferences.setShowEmail(request.getShowEmail());
        if (request.getShowOnlineStatus() != null)
            preferences.setShowOnlineStatus(request.getShowOnlineStatus());

        return userPreferencesRepository.save(preferences);
    }

    @Override
    public UserPreferences getPreferences(Long userId) {
        return userPreferencesRepository.findByUserId(userId)
                .orElseGet(()-> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                    UserPreferences pref= new UserPreferences();
                    pref.setUser(user);
                    return userPreferencesRepository.save(pref);
                });
    }

    @Override
    public Page<User> searchUsers(String query, Long organizationId, Pageable pageable) {
        //TODO: Implement search logic (you'll need to add this method to UserRepository)
        return userRepository.findAll(pageable);
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
