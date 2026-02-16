package cloud.devyard.cloudcollab.repository;

import cloud.devyard.cloudcollab.model.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends JpaRepository<Folder , Long > {
}
