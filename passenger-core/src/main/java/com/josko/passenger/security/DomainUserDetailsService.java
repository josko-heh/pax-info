package com.josko.passenger.security;

import com.josko.passenger.config.Definitions;
import com.josko.passenger.security.entity.User;
import com.josko.passenger.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.josko.passenger.config.Definitions.SECURITY;

@Component("userDetailsService")
@RequiredArgsConstructor
public class DomainUserDetailsService implements UserDetailsService {
    
    private final Logger debugLog = LogManager.getLogger(Definitions.DEBUG_LOGGER);
  
    private final UserRepository userRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
        var user = userRepository
                .findOneByUsername(username)
                .map(this::createSpringSecurityUser)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found in the database"));

        debugLog.debug(SECURITY, "Loaded user {}", username);

        return user;
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(User user) {
        var grantedAuthorities = user
                .getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .toList();
        
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

}
