package cloud.devyard.cloudcollab.repository;

import cloud.devyard.cloudcollab.model.File;
import cloud.devyard.cloudcollab.model.enums.FileStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File , Long> {
    Page<File> findByOrganizationIdAndStatusAndFolderIsNull(
            Long organizationId, FileStatus status, Pageable pageable
    );
}
