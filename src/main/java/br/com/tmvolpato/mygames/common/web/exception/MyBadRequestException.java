package br.com.tmvolpato.mygames.common.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe de exceção para recurso inexistente.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public final class MyBadRequestException extends RuntimeException {

    public MyBadRequestException() {
        super();
    }

    public MyBadRequestException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MyBadRequestException(final String message) {
        super(message);
    }

    public MyBadRequestException(final Throwable cause) {
        super(cause);
    }
}
