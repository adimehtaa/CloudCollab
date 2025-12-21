package cloud.devyard.cloudcollab.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String TRACE_ID_MDC_KEY = "traceId";

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(
            ApplicationException ex,
            WebRequest request) {

        String traceId = getTraceId();
        log.error("Application exception [traceId: {}] - {} : {}",
                traceId, ex.getErrorCode(), ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(ex.getHttpStatus().value())
                .message(ex.getMessage())
                .error(ex.getErrorCode())
                .timestamp(LocalDateTime.now())
                .path(getPath(request))
                .traceId(traceId)
                .details(ex.getDetails())
                .build();

        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex,
            WebRequest request) {

        String traceId = getTraceId();
        log.warn("Validation error [traceId: {}] on path: {}", traceId, getPath(request));

        List<String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Validation failed")
                .error("VALIDATION_ERROR")
                .timestamp(LocalDateTime.now())
                .path(getPath(request))
                .traceId(traceId)
                .details(details)
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            WebRequest request) {

        String traceId = getTraceId();
        log.error("Unexpected error [traceId: {}] - ", traceId, ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An unexpected error occurred. Please contact support with trace ID: " + traceId)
                .error("INTERNAL_SERVER_ERROR")
                .timestamp(LocalDateTime.now())
                .path(getPath(request))
                .traceId(traceId)
                .details(List.of(ex.getClass().getSimpleName()))
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

    // ==================== Helper Methods ====================

    private String getPath(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }

    private String getTraceId() {
        String mdcTraceId = MDC.get(TRACE_ID_MDC_KEY);
        if (mdcTraceId != null) {
            return mdcTraceId;
        }
        return UUID.randomUUID().toString();
    }
}