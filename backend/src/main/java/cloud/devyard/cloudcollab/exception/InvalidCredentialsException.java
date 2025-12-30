package cloud.devyard.cloudcollab.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends ApplicationException{
    public InvalidCredentialsException(String message) {
        super(message, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED");
    }
}
