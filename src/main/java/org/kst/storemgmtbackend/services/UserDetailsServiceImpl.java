package org.kst.storemgmtbackend.services;

import lombok.RequiredArgsConstructor;
import org.kst.storemgmtbackend.models.User;
import org.kst.storemgmtbackend.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Loading Username : " + username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with the given cannot be found."));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .disabled(user.isEnabled())
                .build();
    }
}
