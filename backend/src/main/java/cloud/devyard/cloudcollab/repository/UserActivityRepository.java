package cloud.devyard.cloudcollab.repository;

import cloud.devyard.cloudcollab.model.UserActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActivityRepository extends JpaRepository<UserActivity , Long> {
    public Page<UserActivity> findByUserId(Long userId, Pageable pageable);
    public Page<UserActivity> findByUserOrganizationId(Long organizationId, Pageable pageable);
}
