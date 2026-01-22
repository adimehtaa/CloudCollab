package cloud.devyard.cloudcollab.repository;

import cloud.devyard.cloudcollab.model.UserInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInvitationRepository extends JpaRepository<UserInvitation , Long> {
}
