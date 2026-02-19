package cloud.devyard.cloudcollab.repository;

import cloud.devyard.cloudcollab.model.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {

    List<Folder> findByOrganizationIdAndParentFolderIsNullAndDeletedAtIsNull(Long organizationId);

    List<Folder> findByParentFolderIdAndDeletedAtIsNull(Long parentFolderId);

    Optional<Folder> findByIdAndDeletedAtIsNull(Long id);

    @Query("SELECT f FROM Folder f WHERE f.organization.id = :orgId " +
            "AND f.deletedAt IS NULL " +
            "AND LOWER(f.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Folder> searchFolders(Long orgId, String query);
}
