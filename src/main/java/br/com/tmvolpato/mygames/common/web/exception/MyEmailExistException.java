package br.com.tmvolpato.mygames.common.web.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Email already exist.
 *
 * @author Thiago Michel Volpato
 * @since 2018
 * @version 1.0.0
 */
@ResponseStatus(HttpStatus.CONFLICT)
public final class MyEmailExistException extends DataAccessException {

    public MyEmailExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MyEmailExistException(final String message) {
        super(message);
    }
}
