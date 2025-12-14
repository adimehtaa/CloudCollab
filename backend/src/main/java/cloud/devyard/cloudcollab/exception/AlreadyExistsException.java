package cloud.devyard.cloudcollab.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends ApplicationException {
    public AlreadyExistsException(String message) {
        super(HttpStatus.CONFLICT, "ALREADY_EXISTS", message);
    }
}