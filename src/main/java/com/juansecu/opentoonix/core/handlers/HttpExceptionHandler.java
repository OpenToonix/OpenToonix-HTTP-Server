package com.juansecu.opentoonix.core.handlers;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.juansecu.opentoonix.core.exceptions.IHttpException;
import com.juansecu.opentoonix.core.exceptions.NotFoundException;
import com.juansecu.opentoonix.shared.dtos.responses.BaseResDto;

@ControllerAdvice
@Order(1)
public class HttpExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseResDto handleNotFoundException(
        final IHttpException httpException
    ) {
        return new BaseResDto(httpException.getError(), false);
    }
}
