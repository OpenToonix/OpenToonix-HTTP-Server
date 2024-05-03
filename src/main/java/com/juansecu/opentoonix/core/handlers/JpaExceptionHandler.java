package com.juansecu.opentoonix.core.handlers;

import org.hibernate.exception.JDBCConnectionException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JpaExceptionHandler {
    @ExceptionHandler(JDBCConnectionException.class)
    protected ResponseEntity<Object> handleJdbcConnectionException(
        final JDBCConnectionException jdbcConnectionException
    ) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
