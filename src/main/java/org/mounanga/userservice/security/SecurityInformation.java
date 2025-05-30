package org.mounanga.userservice.security;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mounanga.userservice.exception.NotAuthorizedException;
import org.mounanga.userservice.service.implementation.AuthenticationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@NoArgsConstructor
@Component
public class SecurityInformation {

	private static final Logger log = LoggerFactory.getLogger(SecurityInformation.class);
	
    public String getCurrentUsername() {
        log.info("In getCurrentUsername()");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            throw new NotAuthorizedException("You are not authenticated");
        }
        Object principal = authentication.getPrincipal();
        log.info("current username found");
        if(principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        return principal.toString();
    }
}
