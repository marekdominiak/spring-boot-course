package pl.dominussoft.springbootcourse.app.infrastructure.web;

import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

public class BaseRestController {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        return new ErrorResponse(MessageFormat.format("Invalid Credentials: {0}", ex.getLocalizedMessage()));
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse accessDeniedException(AccessDeniedException ex) {
        return new ErrorResponse(MessageFormat.format("You don't have rights to perform this operation: {0}", ex.getLocalizedMessage()));
    }

    @ExceptionHandler({InsufficientAuthenticationException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse insufficientAuthenticationException(InsufficientAuthenticationException ex) {
        return new ErrorResponse(MessageFormat.format("Insufficient credentials provided {0}", ex.getLocalizedMessage()));
    }

    @Value
    static class ErrorResponse {
        String message;
    }

}
