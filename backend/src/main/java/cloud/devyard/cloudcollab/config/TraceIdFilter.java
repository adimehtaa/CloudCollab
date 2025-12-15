package cloud.devyard.cloudcollab.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class TraceIdFilter extends OncePerRequestFilter {

    private static final String TRACE_ID_HEADER = "X-Trace-Id";
    private static final String TRACE_ID_MDC_KEY = "traceId";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        try {
            String traceId = request.getHeader(TRACE_ID_HEADER);
            if (traceId == null || traceId.trim().isEmpty()) {
                traceId = UUID.randomUUID().toString();
            }

            MDC.put(TRACE_ID_MDC_KEY, traceId);

            response.setHeader(TRACE_ID_HEADER, traceId);

            log.debug("Processing request: {} {} with traceId: {}",
                    request.getMethod(),
                    request.getRequestURI(),
                    traceId);

            // Continue with the filter chain
            filterChain.doFilter(request, response);

        } finally {
            MDC.remove(TRACE_ID_MDC_KEY);
        }
    }
}
