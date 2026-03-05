package cloud.devyard.cloudcollab.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FolderResponse {
    private Long id;
    private String name;
    private String description;
    private String color;
    private Long parentFolderId;
    private Long createdById;
    private String createdByName;
    private Boolean isShared;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
