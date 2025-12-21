package cloud.devyard.cloudcollab.exception;

import org.springframework.http.HttpStatus;

public class RequiredValueException extends ApplicationException {
    public RequiredValueException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "REQUIRED_VALUE_MISSING");
    }
}