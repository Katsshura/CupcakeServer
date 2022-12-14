package com.katsshura.cupcake.api.security.service;

import com.katsshura.cupcake.api.security.jwt.JwtTokenService;
import com.katsshura.cupcake.core.dto.user.UserSignInDTO;
import com.katsshura.cupcake.core.dto.user.UserSignInResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticatorService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public AuthenticatorService(final AuthenticationManager authenticationManager,
                                final JwtTokenService jwtTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
    }

    public UserSignInResponse authenticateUser(final UserSignInDTO userSignInDTO) {
        final var authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userSignInDTO.getEmail(), userSignInDTO.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final var jwt = this.jwtTokenService.generateJwtToken(authentication);
        return new UserSignInResponse(jwt, "Bearer");
    }
}
