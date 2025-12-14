package cloud.devyard.cloudcollab.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private int statusCode;
    private String message;
    private String error;
    private LocalDateTime timestamp;
    private String path;
    private List<String> details;

    public static ErrorResponse of(
            HttpStatus status,
            String message,
            String error,
            String path,
            List<String> details
    ) {
        return new ErrorResponse(
                status.value(),
                message,
                error,
                LocalDateTime.now(),
                path,
                details
        );
    }
}
