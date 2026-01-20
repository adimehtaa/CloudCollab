package cloud.devyard.cloudcollab.service.impl;

import cloud.devyard.cloudcollab.model.User;
import cloud.devyard.cloudcollab.model.UserActivity;
import cloud.devyard.cloudcollab.model.enums.ActivityType;
import cloud.devyard.cloudcollab.repository.UserActivityRepository;
import cloud.devyard.cloudcollab.service.UserActivityService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserActivityServiceImpl implements UserActivityService {

    private final UserActivityRepository userActivityRepository;

    @Override
    public void logActivity(User user, ActivityType activityType, String description, HttpServletRequest request) {
        UserActivity userActivity = UserActivity.builder()
                .user(user)
                .activityType(activityType)
                .description(description)
                .build();

        if (request != null) {
            userActivity.setIpAddress(getClientIpAddress(request));
            userActivity.setUserAgent(
                    Optional.ofNullable(request.getHeader("User-Agent")).orElse("UNKNOWN")
            );
        }

        userActivityRepository.save(userActivity);
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null && !xfHeader.isBlank()) {
            return xfHeader.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    @Override
    public void logActivity(User user, ActivityType activityType, String description) {

    }

    @Override
    public Page<UserActivity> getUserActivities(Long userId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<UserActivity> getOrganizationActivities(Long organizationId, Pageable pageable) {
        return null;
    }
}
