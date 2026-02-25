package cloud.devyard.cloudcollab.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileResponse {
    private Long id;
    private String name;
    private String description;
    private String mimeType;
    private Long size;
    private String fileExtension;
    private Integer version;
    private Boolean isPublic;
    private String shareToken;
    private Long uploadedById;
    private String uploadedByName;
    private Long folderId;
    private String folderName;
    private String downloadUrl;
    private LocalDateTime uploadedAt;
    private LocalDateTime updatedAt;
}
