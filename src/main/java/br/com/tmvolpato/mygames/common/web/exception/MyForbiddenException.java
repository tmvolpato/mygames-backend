package br.com.tmvolpato.mygames.common.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe de exceção para recurso proibido.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class MyForbiddenException extends RuntimeException {

    public MyForbiddenException() {
        super();
    }

    public MyForbiddenException(final String message) {
        super(message);
    }

    public MyForbiddenException(final Throwable cause) {
        super(cause);
    }
}
