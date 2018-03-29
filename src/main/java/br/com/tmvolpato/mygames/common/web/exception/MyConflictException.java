package br.com.tmvolpato.mygames.common.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe de exceção para conflito.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
@ResponseStatus(HttpStatus.CONFLICT)
public final class MyConflictException extends RuntimeException {

    public MyConflictException() {
        super();
    }

    public MyConflictException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MyConflictException(final String message) {
        super(message);
    }

    public MyConflictException(final Throwable cause) {
        super(cause);
    }
}
