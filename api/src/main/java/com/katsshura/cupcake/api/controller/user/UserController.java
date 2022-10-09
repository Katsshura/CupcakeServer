package com.katsshura.cupcake.api.controller.user;

import com.katsshura.cupcake.api.security.service.AuthenticatorService;
import com.katsshura.cupcake.core.dto.user.UserDTO;
import com.katsshura.cupcake.core.dto.user.UserSignInDTO;
import com.katsshura.cupcake.core.dto.user.UserSignInResponse;
import com.katsshura.cupcake.core.dto.user.payment.UserPaymentCardDTO;
import com.katsshura.cupcake.core.services.user.UserService;
import com.katsshura.cupcake.core.services.user.payment.UserPaymentCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final AuthenticatorService authenticatorService;

    private final UserPaymentCardService userPaymentCardService;

    public UserController(final UserService userService,
                          final AuthenticatorService authenticatorService,
                          final UserPaymentCardService userPaymentCardService) {
        this.userService = userService;
        this.authenticatorService = authenticatorService;
        this.userPaymentCardService = userPaymentCardService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        log.debug("Received request to create new user with info: {}", userDTO);

        final var result = this.userService.persistUser(userDTO);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<UserSignInResponse> singIn(@RequestBody @Valid UserSignInDTO userSignInDTO) {
        return ResponseEntity.ok(this.authenticatorService.authenticateUser(userSignInDTO));
    }

    @PostMapping("/{id}/payment/card")
    public ResponseEntity<UserPaymentCardDTO> savePaymentCard(@RequestBody @Valid UserPaymentCardDTO paymentCardDTO,
                                                              @PathVariable Long id) {
        final var result = this.userPaymentCardService.saveNewPaymentCard(paymentCardDTO, id);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/payment/card")
    public ResponseEntity<List<UserPaymentCardDTO>> getPaymentCards(@PathVariable Long id) {
        return ResponseEntity.ok(this.userPaymentCardService.getUserPayments(id));
    }

    @DeleteMapping("/{userId}/payment/card/{id}")
    public ResponseEntity<List<UserPaymentCardDTO>> deletePaymentCard(@PathVariable Long userId,
                                                                      @PathVariable Long id) {
        this.userPaymentCardService.deletePayment(id, userId);
        return ResponseEntity.ok().build();
    }
}
