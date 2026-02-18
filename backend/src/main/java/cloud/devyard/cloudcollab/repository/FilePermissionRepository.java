package cloud.devyard.cloudcollab.repository;

import cloud.devyard.cloudcollab.model.FilePermission;
import cloud.devyard.cloudcollab.model.enums.PermissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilePermissionRepository extends JpaRepository<FilePermission, Long> {

    List<FilePermission> findByFileId(Long fileId);

    Optional<FilePermission> findByFileIdAndUserId(Long fileId, Long userId);

    List<FilePermission> findByUserIdAndPermissionType(Long userId, PermissionType permissionType);

    void deleteByFileIdAndUserId(Long fileId, Long userId);
}
