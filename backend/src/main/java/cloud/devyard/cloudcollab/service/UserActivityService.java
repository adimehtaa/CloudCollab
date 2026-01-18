package cloud.devyard.cloudcollab.service;

import cloud.devyard.cloudcollab.model.User;
import cloud.devyard.cloudcollab.model.UserActivity;
import cloud.devyard.cloudcollab.model.enums.ActivityType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserActivityService {
    public void logActivity(User user, ActivityType activityType, String description, HttpServletRequest request);
    public void logActivity(User user, ActivityType activityType, String description);
    public Page<UserActivity> getUserActivities(Long userId, Pageable pageable);
    public Page<UserActivity> getOrganizationActivities(Long organizationId, Pageable pageable);
}
