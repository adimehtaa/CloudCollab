package cloud.devyard.cloudcollab.dto.request;

import cloud.devyard.cloudcollab.model.enums.PermissionType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShareFileRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Permission type is required")
    private PermissionType permissionType;
}
