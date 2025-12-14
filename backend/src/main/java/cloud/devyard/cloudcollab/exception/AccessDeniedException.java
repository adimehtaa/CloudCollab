package cloud.devyard.cloudcollab.exception;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends ApplicationException {
    public AccessDeniedException(String message) {
        super(HttpStatus.FORBIDDEN, "ACCESS_DENIED", message);
    }
}