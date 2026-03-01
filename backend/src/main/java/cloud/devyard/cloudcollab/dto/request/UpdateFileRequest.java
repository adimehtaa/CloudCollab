package cloud.devyard.cloudcollab.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFileRequest {
    private String name;
    private String description;
    private Long folderId;
}
