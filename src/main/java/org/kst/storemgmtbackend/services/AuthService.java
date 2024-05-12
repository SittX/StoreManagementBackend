package org.kst.storemgmtbackend.services;

import lombok.RequiredArgsConstructor;
import org.kst.storemgmtbackend.dtos.LoginRequest;
import org.kst.storemgmtbackend.dtos.LoginResponse;
import org.kst.storemgmtbackend.dtos.RegisterRequest;
import org.kst.storemgmtbackend.dtos.RegisterResponse;
import org.kst.storemgmtbackend.exceptions.CustomIllegalStateException;
import org.kst.storemgmtbackend.mappers.UserMapper;
import org.kst.storemgmtbackend.models.User;
import org.kst.storemgmtbackend.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

import static org.kst.storemgmtbackend.models.Role.ADMIN;
import static org.kst.storemgmtbackend.models.Role.USER;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public RegisterResponse registerUser(final RegisterRequest registerRequest) throws CustomIllegalStateException {
        User user = this.userMapper.mapToUserModel(registerRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(USER, ADMIN));

        boolean isEmailExist = this.userRepository.existsByEmail(user.getEmail());
        boolean isUsernameExist = this.userRepository.existsByUsername(user.getUsername());

        if (isEmailExist) {
            throw new CustomIllegalStateException("Email already exists.");
        }

        if (isUsernameExist) {
            throw new CustomIllegalStateException("Username already exists.");
        }

        String jws = this.jwtService.generateToken(user);
        this.userRepository.save(user);

        // Validation email sending logic will go here. In fact, we will only send the JWT token once the user has verified their email.

        return RegisterResponse.builder()
                .token(jws)
                .message("Validation email has been sent. Please validate to activate your account.")
                .status(HttpStatus.OK.name())
                .build();
    }

    public LoginResponse loginUser(final LoginRequest loginRequest) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        String jws = jwtService.generateToken((UserDetails) authentication);

        return LoginResponse.builder()
                .token(jws)
                .status(HttpStatus.OK.name())
                .message("Successfully logged in.")
                .build();
    }


}
