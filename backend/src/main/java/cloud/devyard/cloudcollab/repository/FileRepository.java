package cloud.devyard.cloudcollab.repository;

import cloud.devyard.cloudcollab.model.File;
import cloud.devyard.cloudcollab.model.enums.FileStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    Page<File> findByOrganizationIdAndStatusAndFolderIsNull(
            Long organizationId, FileStatus status, Pageable pageable);

    Page<File> findByOrganizationIdAndStatusAndFolderId(
            Long organizationId, FileStatus status, Long folderId, Pageable pageable);

    List<File> findByUploadedByIdAndStatus(Long userId, FileStatus status);

    Optional<File> findByShareToken(String shareToken);

    @Query("SELECT f FROM File f WHERE f.organization.id = :orgId AND f.status = :status " +
            "AND (LOWER(f.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(f.description) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<File> searchFiles(@Param("orgId") Long organizationId,
                           @Param("status") FileStatus status,
                           @Param("query") String query,
                           Pageable pageable);

    @Query("SELECT SUM(f.size) FROM File f WHERE f.organization.id = :orgId AND f.status = :status")
    Long getTotalStorageUsed(@Param("orgId") Long organizationId, @Param("status") FileStatus status);

    List<File> findByParentFileIdOrderByVersionDesc(Long parentFileId);
}
