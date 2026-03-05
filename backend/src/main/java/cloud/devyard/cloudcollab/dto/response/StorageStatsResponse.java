package cloud.devyard.cloudcollab.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StorageStatsResponse {
    private Long usedStorage; // in bytes
    private Long totalQuota; // in bytes
    private Long availableStorage; // in bytes
    private Double usagePercentage;
}
