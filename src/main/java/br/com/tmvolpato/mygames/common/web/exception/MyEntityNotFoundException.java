package br.com.tmvolpato.mygames.common.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe exceção para recurso não encontrado.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public final class MyEntityNotFoundException extends RuntimeException {

    public MyEntityNotFoundException() {
        super();
    }

    public MyEntityNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MyEntityNotFoundException(final String message) {
        super(message);
    }

    public MyEntityNotFoundException(final Throwable cause) {
        super(cause);
    }
}
