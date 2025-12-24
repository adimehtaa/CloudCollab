package cloud.devyard.cloudcollab.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = jwtTokenProvider.getJwtFromHeader(request);

            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateJwtToken(jwt)){
                Long userId = jwtTokenProvider.getUserIdFromJwtToken(jwt);
            }

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        filterChain.doFilter(request , response);


    }
}
