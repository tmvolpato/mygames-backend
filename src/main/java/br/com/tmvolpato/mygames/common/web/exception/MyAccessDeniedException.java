package br.com.tmvolpato.mygames.common.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe de exceção para acesso negado.
 *
 * @author Thiago Michel Volpato
 * @since 2018
 * @version 1.0.0
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class MyAccessDeniedException extends RuntimeException {

    public MyAccessDeniedException() {
        super();
    }

    public MyAccessDeniedException(final String message) {
        super(message);
    }
}
