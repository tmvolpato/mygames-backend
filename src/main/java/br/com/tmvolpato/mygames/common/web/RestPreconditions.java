package br.com.tmvolpato.mygames.common.web;

import br.com.tmvolpato.mygames.common.web.exception.MyBadRequestException;
import br.com.tmvolpato.mygames.common.web.exception.MyConflictException;
import br.com.tmvolpato.mygames.common.web.exception.MyForbiddenException;
import br.com.tmvolpato.mygames.common.web.exception.MyResourceNotFoundException;

/**
 * Classe de tratamento para os recursos.
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
     * Objeto passado como parametro não pode ser nulo.
     *
     * @param reference
     * @param <T>
     * @return not null referente ao que foi validado
     */
    public static <T> T checkNotNull(final T reference) {
        return checkNotNull(reference, null);
    }

    /**
     * Valor booelano indicando se ele é nulo.
     *
     * @param exists
     * @return not null referente ao que foi validado
     */
    public static void checkNotNull(final boolean exists) {
        checkNotNull(exists, null);
    }

    /**
     *Objeto passado como parametro não pode ser nulo e define uma mensagem.
     *
     * @param reference
     * @param message
     * @param <T>
     * @return not null referente ao que foi validado com mensagem
     */
    public static <T> T checkNotNull(final T reference, final String message) {
        if (reference == null) {
            throw new MyResourceNotFoundException(message);
        }
        return reference;
    }

    /**
     * Valor booelano indicando se ele é nulo e define uma mensagem.
     *
     * @return not null referente ao que foi validado com mensagem.
     * @param entityExists
     * @param message
     */
    public static void checkNotNull(final boolean entityExists, final String message) {
        if (!entityExists) {
            throw new MyResourceNotFoundException(message);
        }
    }

    /**
     * Referência passada um parâmetro para o método de chamada não é nulo.
     *
     * @param reference
     * @param <T>
     * @return not null referente ao que foi validado
     */
    public static <T> T checkRequestElementNotNull(final T reference) {
        return checkRequestElementNotNull(reference, null);
    }

    /**
     * Referência passada um parâmetro para o método de chamada não é nulo.
     *
     * @param reference
     * @param message
     * @param <T>
     * @return
     */
    public static <T> T checkRequestElementNotNull(final T reference, final String message) {
        if (reference == null) {
            throw new MyBadRequestException(message);
        }
        return reference;
    }

    /**
     * Garante a verdade de uma expressão.
     *
     * @param expression
     */
    public static void checkRequestState(final boolean expression) {
        checkRequestState(expression, null);
    }

    /**
     * Garante a verdade de uma expressão.
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
     * Garante a verdade da expressão.
     *
     * @param expression
     */
    public static void checkIfBadRequest(final boolean expression) {
        checkIfBadRequest(expression, null);
    }

    /**
     * Garante a verdade de uma expressão e relacionada à validade do pedido
     *
     * @param expression
     * @param message
     */
    public static void checkIfBadRequest(final boolean expression, final String message) {
        if (!expression) {
            throw new MyBadRequestException(message);
        }
    }

    /**
     * Verifica se algum valor foi encontrado, caso contrario lança exceção.
     *
     * @param expression
     */
    public static void checkFound(final boolean expression) {
        checkFound(expression, null);
    }

    /**
     * Verifica se algum valor foi encontrado, caso contrario lança exceção.
     *
     * @param expression
     * @param message
     */
    public static void checkFound(final boolean expression, final String message) {
        if (!expression) {
            throw new MyResourceNotFoundException(message);
        }
    }

    /**
     * Verifica se algum valor foi encontrado, caso contrario lança exceção.
     *
     * @param resource
     * @param <T>
     * @return
     */
    public static <T> T checkFound(final T resource) {
        return checkFound(resource, null);
    }

    /**
     * Verifica se algum valor foi encontrado, caso contrario lança exceção.
     *
     * @param resource
     * @param message
     * @param <T>
     * @return
     */
    public static <T> T checkFound(final T resource, final String message) {
        if (resource == null) {
            throw new MyResourceNotFoundException(message);
        }

        return resource;
    }

    /**
     * Verifica se algum valor foi encontrado, caso contrario lança exceção.
     *
     * @param expression
     */
    public static void checkAllowed(final boolean expression) {
        checkAllowed(expression, null);
    }

    /**
     * Verifica se algum valor foi encontrado, caso contrario lança exceção.
     *
     * @param expression
     * @param message
     */
    public static void checkAllowed(final boolean expression, final String message) {
        if (!expression) {
            throw new MyForbiddenException(message);
        }
    }

}
