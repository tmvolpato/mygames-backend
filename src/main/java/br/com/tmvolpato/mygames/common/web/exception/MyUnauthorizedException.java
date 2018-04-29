package br.com.tmvolpato.mygames.common.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * No authorized class exception.
 *
 * @author Thiago Michel Volpato
 * @since 2018
 * @version 1.0.0
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class MyUnauthorizedException extends RuntimeException {

    public MyUnauthorizedException() {
        super();
    }

    public MyUnauthorizedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MyUnauthorizedException(final String message) {
        super(message);
    }

    public MyUnauthorizedException(final Throwable cause) {
        super(cause);
    }
}
