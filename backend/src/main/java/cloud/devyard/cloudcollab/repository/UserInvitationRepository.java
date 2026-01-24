package cloud.devyard.cloudcollab.repository;

import cloud.devyard.cloudcollab.model.UserInvitation;
import cloud.devyard.cloudcollab.model.enums.InvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserInvitationRepository extends JpaRepository<UserInvitation , Long> {
    boolean existsByEmailAndOrganizationIdAndInvitationStatus(String email, Long organizationId, InvitationStatus status);
    List<UserInvitation> findByOrganizationIdAndInvitationStatus(Long organizationId ,InvitationStatus status);
    List<UserInvitation> findByInvitationStatusAndExpiresAtBefore(InvitationStatus status, LocalDateTime now);
}
