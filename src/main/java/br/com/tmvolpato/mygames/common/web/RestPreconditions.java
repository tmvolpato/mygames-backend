package br.com.tmvolpato.mygames.common.web;

import br.com.tmvolpato.mygames.common.web.exception.MyBadRequestException;
import br.com.tmvolpato.mygames.common.web.exception.MyConflictException;
import br.com.tmvolpato.mygames.common.web.exception.MyResourceNotFoundException;

/**
 * Preconditions.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
public final class RestPreconditions {

    private RestPreconditions() {
        throw new AssertionError();
    }

    /**
     * Check parameter is not null if different launches exeption not found.
     *
     * @param reference
     * @param <T>
     * @return object
     */
    public static <T> T checkNotNull(final T reference) {
        return checkNotNull(reference, null);
    }

    /**
     * Check boolean if different of true launches exception not found.
     *
     * @param exists
     */
    public static void checkNotNull(final boolean exists) {
        checkNotNull(exists, null);
    }

    /**
     * Check parameter if null launches exception not found with message
     *
     * @param reference
     * @param message
     * @param <T>
     * @return object
     */
    public static <T> T checkNotNull(final T reference, final String message) {
        if (reference == null) {
            throw new MyResourceNotFoundException(message);
        }
        return reference;
    }

    /**
     * Check boolean if different of true launches exception not found with message.
     *
     * @param entityExists
     * @param message
     */
    public static void checkNotNull(final boolean entityExists, final String message) {
        if (!entityExists) {
            throw new MyResourceNotFoundException(message);
        }
    }

    /**
     * Check parameter if null launches exception bad request.
     *
     * @param reference
     * @param <T>
     * @return object
     */
    public static <T> T checkRequestElementNotNull(final T reference) {
        return checkRequestElementNotNull(reference, null);
    }

    /**
     * Check parameter if null launches exception bad request with message.
     *
     * @param reference
     * @param message
     * @param <T>
     * @return object
     */
    public static <T> T checkRequestElementNotNull(final T reference, final String message) {
        if (reference == null) {
            throw new MyBadRequestException(message);
        }
        return reference;
    }

    /**
     * Check boolean if different of true launches exception conflict.
     *
     * @param expression
     */
    public static void checkRequestState(final boolean expression) {
        checkRequestState(expression, null);
    }

    /**
     * Check boolean if different of true launches exception conflict with message
     *
     * @param expression
     * @param message
     */
    public static void checkRequestState(final boolean expression, final String message) {
        if (!expression) {
            throw new MyConflictException(message);
        }
    }

    /**
     * Check boolean if different of true launches exception bad request.
     *
     * @param expression
     */
    public static void checkIfBadRequest(final boolean expression) {
        checkIfBadRequest(expression, null);
    }

    /**
     * Check boolean if different of true launches exception bad request with message.
     *
     * @param expression
     * @param message
     */
    public static void checkIfBadRequest(final boolean expression, final String message) {
        if (!expression) {
            throw new MyBadRequestException(message);
        }
    }
}
