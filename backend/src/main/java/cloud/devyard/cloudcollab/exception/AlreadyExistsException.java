package cloud.devyard.cloudcollab.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends ApplicationException {
    public AlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT, "ALREADY_EXISTS");
    }
}