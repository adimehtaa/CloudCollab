package cloud.devyard.cloudcollab.service.impl;

import cloud.devyard.cloudcollab.model.User;
import cloud.devyard.cloudcollab.model.UserActivity;
import cloud.devyard.cloudcollab.model.enums.ActivityType;
import cloud.devyard.cloudcollab.service.UserActivityService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserActivityServiceImpl implements UserActivityService {
    @Override
    public void logActivity(User user, ActivityType activityType, String description, HttpServletRequest request) {

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
