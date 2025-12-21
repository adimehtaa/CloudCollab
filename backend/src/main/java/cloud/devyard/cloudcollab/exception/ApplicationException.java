package cloud.devyard.cloudcollab.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ApplicationException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final List<String> details;

    public ApplicationException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = "APP-" + httpStatus.value();
        this.details = new ArrayList<>();
    }

    public ApplicationException(String message, HttpStatus httpStatus, String errorCode) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.details = new ArrayList<>();
    }

    public ApplicationException(String message, HttpStatus httpStatus, String errorCode, List<String> details) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.details = details != null ? details : new ArrayList<>();
    }

    public ApplicationException(String message, HttpStatus httpStatus, String errorCode, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.details = new ArrayList<>();
    }
}