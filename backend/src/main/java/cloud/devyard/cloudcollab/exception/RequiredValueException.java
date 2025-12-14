package cloud.devyard.cloudcollab.exception;

import org.springframework.http.HttpStatus;

public class RequiredValueException extends ApplicationException {
    public RequiredValueException(String message) {
        super(HttpStatus.BAD_REQUEST, "REQUIRED_VALUE_MISSING", message);
    }
}