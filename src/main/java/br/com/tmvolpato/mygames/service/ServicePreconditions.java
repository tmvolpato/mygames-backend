package br.com.tmvolpato.mygames.service;

import br.com.tmvolpato.mygames.common.web.exception.MyBadRequestException;
import br.com.tmvolpato.mygames.common.web.exception.MyEntityNotFoundException;
import org.springframework.util.StringUtils;

/**
 * Pre condições de exception do serviço.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
public final class ServicePreconditions {

    private ServicePreconditions() {
        throw new AssertionError();
    }

    /**
     * Entidade existe.
     *
     * @param entity
     * @param <T>
     * @return
     */
    public static <T> T checkEntityExists(final T entity) {
        if (entity == null) {
            throw new MyEntityNotFoundException();
        }
        return entity;
    }

    /**
     * Entidade existe.
     *
     * @param entityExists
     */
    public static void checkEntityExists(final boolean entityExists) {
        if (!entityExists) {
            throw new MyEntityNotFoundException();
        }
    }

    /**
     * Verifica o parametro passado.
     * @param value
     */
    public static void checkParameterLong(final Long value) {
        if (value == null) {
            throw new MyBadRequestException();
        }
    }

    /**
     * Verifica o parametro passado.
     * @param value
     */
    public static void checkParameterString(final String value) {
        if (StringUtils.isEmpty(value)) {
            throw new MyBadRequestException();
        }
    }

    /**
     * Verifica o argumento passado.
     * @param okArgument
     */
    public static void checkOKArgument(final boolean okArgument) {
        if (!okArgument) {
            throw new MyBadRequestException();
        }
    }
}
