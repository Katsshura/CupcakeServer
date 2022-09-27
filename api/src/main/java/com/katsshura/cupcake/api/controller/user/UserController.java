package com.katsshura.cupcake.api.controller.user;

import com.katsshura.cupcake.api.security.service.AuthenticatorService;
import com.katsshura.cupcake.core.dto.user.UserDTO;
import com.katsshura.cupcake.core.dto.user.UserSignInDTO;
import com.katsshura.cupcake.core.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final AuthenticatorService authenticatorService;

    public UserController(final UserService userService, final AuthenticatorService authenticatorService) {
        this.userService = userService;
        this.authenticatorService = authenticatorService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        log.debug("Received request to create new user with info: {}", userDTO);

        final var result = this.userService.persistUser(userDTO);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/signin")
    public ResponseEntity<String> singIn(@RequestBody @Valid UserSignInDTO userSignInDTO) {
        final var result = this.authenticatorService.authenticateUser(userSignInDTO);
        return ResponseEntity.ok(result);
    }
}
