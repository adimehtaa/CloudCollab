package cloud.devyard.cloudcollab.repository;

import cloud.devyard.cloudcollab.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization , Long> {
    Optional<Organization> findBySlug(String slug);
    Boolean existsBySlug(String slug);
}
