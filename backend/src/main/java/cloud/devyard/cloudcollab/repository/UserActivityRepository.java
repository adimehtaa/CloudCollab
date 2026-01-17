package cloud.devyard.cloudcollab.repository;

import cloud.devyard.cloudcollab.model.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActivityRepository extends JpaRepository<UserActivity , Long> {
}
