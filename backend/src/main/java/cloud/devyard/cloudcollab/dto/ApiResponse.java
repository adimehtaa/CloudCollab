package cloud.devyard.cloudcollab.dto;

import cloud.devyard.cloudcollab.dto.response.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private Status status;
    private Integer code;
    private T data;

    @Builder.Default
    private Instant timestamp = new Date().toInstant();
}
