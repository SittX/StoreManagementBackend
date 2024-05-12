package org.kst.storemgmtbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.kst.storemgmtbackend.dtos.LoginRequest;
import org.kst.storemgmtbackend.dtos.LoginResponse;
import org.kst.storemgmtbackend.dtos.RegisterRequest;
import org.kst.storemgmtbackend.dtos.RegisterResponse;
import org.kst.storemgmtbackend.exceptions.CustomIllegalStateException;
import org.kst.storemgmtbackend.services.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody final RegisterRequest registerRequest) {
        RegisterResponse response;
        try {
            response = this.authService.registerUser(registerRequest);
        } catch (CustomIllegalStateException e) {
            return handleRegisterException(e, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return handleRegisterException(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody final LoginRequest loginRequest) {
        LoginResponse response;
        try {
            response = authService.loginUser(loginRequest);
        }catch (BadCredentialsException be) {
            return handleLoginException(be,HttpStatus.BAD_REQUEST);
        }
        catch(AuthenticationException ac){
            return handleLoginException(ac, HttpStatus.UNAUTHORIZED);
        }catch(Exception ex){
            return handleLoginException(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/activate")
    public void activateUserAccount(@PathVariable final String activationToken) {

    }

    private static ResponseEntity<RegisterResponse> handleRegisterException(Exception e, HttpStatus httpStatus) {
        logger.error(e.getMessage());
        e.printStackTrace();

        RegisterResponse serverErrorResponse = RegisterResponse.builder()
                .status(httpStatus.name())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(serverErrorResponse, httpStatus);
    }

    private ResponseEntity<LoginResponse> handleLoginException(final Exception ex, final HttpStatus httpStatus) {
        logger.error(ex.getMessage());
        ex.printStackTrace();

        LoginResponse response = LoginResponse.builder()
            .message(ex.getMessage())
            .status(httpStatus.name())
            .build();

        return ResponseEntity.status(httpStatus).body(response);
    }
}
