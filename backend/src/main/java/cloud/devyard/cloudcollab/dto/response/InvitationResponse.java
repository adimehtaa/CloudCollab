package cloud.devyard.cloudcollab.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitationResponse {
    private Long id;
    private String email;
    private String roleName;
    private String invitedByName;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
}
