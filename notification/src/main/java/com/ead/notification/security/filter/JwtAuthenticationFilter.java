package com.ead.notification.security.filter;

import com.ead.notification.responses.ErrorResponse;
import com.ead.notification.security.JwtProvider;
import com.ead.notification.security.UserDetailsImpl;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_STRING = "Authorization";
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(HEADER_STRING);
        final String jwt;
        final UUID userId;
        final String rolesStr;

        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            jwt = authHeader.substring(7);
            userId = UUID.fromString(jwtProvider.extractSubject(jwt));
            rolesStr = jwtProvider.extractClaimName(jwt, "authorities");

            if (StringUtils.isNotEmpty(userId.toString()) && SecurityContextHolder.getContext().getAuthentication() == null) {
                var userDetails = UserDetailsImpl.build(userId, rolesStr);

                if (jwtProvider.isTokenValid(jwt, userDetails)) {
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    context.setAuthentication(authToken);
                    SecurityContextHolder.setContext(context);
                }
            }

        } catch (JwtException e) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, "JWT Error", e);
        } catch (Exception e) {
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, "Ocorreu um erro no sistema, por favor contacte o administrador", e);
        }
        filterChain.doFilter(request, response);
    }

    /* Since the exception is not raised from a controller but a filter,
@ControllerAdvice won't catch it. So this method will handle this internal error */
    private void setErrorResponse(HttpStatus status, HttpServletResponse response, String message, Throwable ex) {
        // A class used for errors
        var apiError = new ErrorResponse(status, message, ex.getLocalizedMessage());

        try {
            String json = apiError.convertToJson();
            System.out.println(json);
            response.getWriter().write(json);
        } catch (IOException e) {
            log.error(JwtAuthenticationFilter.class.getName() + ": " + e.getLocalizedMessage());
        }
    }
}
