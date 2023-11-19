package com.ead.course.security.annotation.aopmethod;

import com.ead.course.security.UserDetailsImpl;
import com.ead.course.security.annotation.HasProperAuthority;
import com.ead.course.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class HasProperAuthorityAspect {
    private final UserService userService;

    @Before("within(@org.springframework.web.bind.annotation.RestController *) && @annotation(authorities)")
    public void hasAuthorities(final JoinPoint joinPoint, final HasProperAuthority authorities) {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        if (!Objects.isNull(securityContext)) {
            final Authentication authentication = securityContext.getAuthentication();
            if (!Objects.isNull(authentication)) {
                var principal = ((UserDetailsImpl) authentication.getPrincipal()).getId();
                var user = userService.fetchUserById(principal);
                final Collection<? extends GrantedAuthority> userAuthorities = authentication.getAuthorities();
                if (Stream.of(authorities.authorities()).noneMatch(authName -> userAuthorities.stream().anyMatch(userAuthority -> authName.name().equals(userAuthority.getAuthority())))) {
                    log.error("User {} doesn't have the correct authorities required by endpoint", user.getFullName());
                    throw new AccessDeniedException(user.getFullName() + " doesn't have the correct authorities to perform this action");
                }
            } else {
                log.error("The Authentication is null when checking endpoint access for user request");
                throw new AccessDeniedException("The Authentication is null when checking endpoint access for user request");
            }
        } else {
            log.error("The security context is null when checking endpoint access for user request");
            throw new AccessDeniedException("The security context is null when checking endpoint access for user request");
        }
    }
}
