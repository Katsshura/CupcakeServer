package com.katsshura.cupcake.api.controller.handler;

import com.katsshura.cupcake.api.model.response.ResponseErrorModel;
import com.katsshura.cupcake.core.exceptions.AlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@Controller
public class ExceptionHandlerController {

    @ExceptionHandler(AlreadyExistsException.class)
    public final ResponseEntity<ResponseErrorModel> handleAlreadyExistsException(final AlreadyExistsException e) {
        return this.buildErrorResponse(e, HttpStatus.CONFLICT);
    }

    private ResponseEntity<ResponseErrorModel> buildErrorResponse(final Exception cause,
                                                                  final HttpStatus statusCode) {
        final ResponseErrorModel response = ResponseErrorModel.builder().reason(cause).build();
        return new ResponseEntity<>(response, statusCode);
    }
}
