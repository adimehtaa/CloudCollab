package cloud.devyard.cloudcollab.repository;

import cloud.devyard.cloudcollab.model.UserInvitation;
import cloud.devyard.cloudcollab.model.enums.InvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInvitationRepository extends JpaRepository<UserInvitation , Long> {
    boolean existsByEmailAndOrganizationIdAndStatus(String email, Long organizationId, InvitationStatus status);


}
