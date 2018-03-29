package br.com.tmvolpato.mygames.common.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe de exceção para recurso não encontrdo.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public final class MyResourceNotFoundException extends RuntimeException {

    public MyResourceNotFoundException() {
        super();
    }

    public MyResourceNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MyResourceNotFoundException(final String message) {
        super(message);
    }

    public MyResourceNotFoundException(final Throwable cause) {
        super(cause);
    }

}
