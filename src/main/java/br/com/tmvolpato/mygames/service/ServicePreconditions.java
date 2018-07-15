package br.com.tmvolpato.mygames.service;

import br.com.tmvolpato.mygames.common.web.exception.*;
import org.springframework.util.StringUtils;

/**
 * Preconditions service.
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
     * Check entity if is null launches exception not found.
     *
     * @param entity
     * @param <T>
     * @return object
     */
    public static <T> T checkEntityExists(final T entity) {
        if (entity == null) {
            throw new MyEntityNotFoundException();
        }
        return entity;
    }

    /**
     * Check entity if different of true launches exception not found.
     *
     * @param entityExists
     */
    public static void checkEntityExists(final boolean entityExists) {
        if (!entityExists) {
            throw new MyEntityNotFoundException("Entity not found");
        }
    }

    /**
     * Check e-mail already exist if true launches exception conflict.
     *
     * @param exist
     */
    public static void checkEmailExist(final boolean exist) {
        if (exist) {
            throw new MyEmailExistException("This e-mail already exist");
        }
    }

    /**
     * Check parameter if is null launches exception bad request.
     *
     * @param value
     */
    public static void checkParameterLong(final Long value) {
        if (value == null) {
            throw new MyBadRequestException();
        }
    }

    /**
     * Check parameter if empty launches exception bad request.
     *
     * @param value
     */
    public static void checkParameterString(final String value) {
        if (StringUtils.isEmpty(value)) {
            throw new MyBadRequestException();
        }
    }

    /**
     * Check if user of application is different is null and launches exception forbidden.
     *
     * @param object
     */
    public static void checkUserApplication(final Object object) {
        if (object == null) {
            throw new MyAccessDeniedException("User application is null");
        }
    }

    /**
     * Check argument if different of true launches exception bad request.
     *
     * @param okArgument
     */
    public static void checkOKArgument(final boolean okArgument) {
        if (!okArgument) {
            throw new MyBadRequestException();
        }
    }

    /**
     * Check value already exist and launches exception conflict.
     *
     * @param value
     */
    public static void checkIfAlreadyExist(final boolean value) {
       if (value) {
           throw new MyConflictException();
       }
    }

    /**
     * Check if the user ou resource is null and launches exception bad request and
     * check if user is different owner of resource and launches exception not found.
     *
     * @param ownerId
     * @param resourceId
     */
    public static void checkResourceOwner(final Long ownerId, final Long resourceId) {

        if (ownerId == null || resourceId == null) {
            throw new MyBadRequestException();
        }

        if (!ownerId.equals(resourceId)) {
            throw new MyResourceNotFoundException();
        }
    }
}
