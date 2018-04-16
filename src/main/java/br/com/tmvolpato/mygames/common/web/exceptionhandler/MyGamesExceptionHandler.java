package br.com.tmvolpato.mygames.common.web.exceptionhandler;

import br.com.tmvolpato.mygames.common.web.exception.MyConflictException;
import br.com.tmvolpato.mygames.common.web.exception.MyEntityNotFoundException;
import br.com.tmvolpato.mygames.common.web.exception.MyResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.util.InvalidMimeTypeException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe que exibe a mensagem de erro para o usuário e desenvolvedor.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @since 2017
 */
@ControllerAdvice
public class MyGamesExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Logger.
     */
    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Mensagem i18n.
     */
    private final MessageSource messageSource;

    @Autowired
    public MyGamesExceptionHandler(final MessageSource messageSource) {
        super();
        this.messageSource = messageSource;
    }

    @Override
    protected final ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers, HttpStatus status, final WebRequest request) {
        return handleExceptionInternal(ex, this.createMessageError("message.invalid", this.messageDevCheked(ex.getLocalizedMessage())), headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected final ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                        final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        return handleExceptionInternal(ex, this.createFieldMessageError(ex.getBindingResult()), headers, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Erro: 401
     * @return
     */
    @ExceptionHandler(value = { InvalidTokenException.class, UnauthorizedClientException.class, UnauthorizedUserException.class })
    public final ResponseEntity<Object> handleAuthenticationCredentialsNotFoundException(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex, this.createMessageError("unathorized", this.messageDevCheked(ex.getLocalizedMessage())), new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    /**
     * Erro: 403
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { AccessDeniedException.class })
    public ResponseEntity<Object> handleAccessDeniedException(final AccessDeniedException ex, final WebRequest request) {
        return handleExceptionInternal(ex, this.createMessageError("forbidden.access", this.messageDevCheked(ex.getLocalizedMessage())), new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    /**
     * Erro: 404
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { EntityNotFoundException.class, MyEntityNotFoundException.class, MyResourceNotFoundException.class })
    public final ResponseEntity<Object> handleNotFoundException(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex, this.createMessageError("resource.not.found", this.messageDevCheked(ex.getLocalizedMessage())), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    /**
     * Erro: 405
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { MethodNotAllowedException.class })
    public  final ResponseEntity<Object> handleMethodNotAllowedException(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex, this.createMessageError("method.not.allowed", this.messageDevCheked(ex.getLocalizedMessage())), new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED, request);
    }

    /**
     * Erro: 409
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { InvalidDataAccessApiUsageException.class, DataAccessException.class, MyConflictException.class, ObjectOptimisticLockingFailureException.class, OptimisticLockingFailureException.class})
    public final ResponseEntity<Object> handleConflictException(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex, this.createMessageError("resource.conflict", this.messageDevCheked(ex.getLocalizedMessage())), new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    /**
     * Erro: 415
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { InvalidMimeTypeException.class, InvalidMediaTypeException.class })
    public final ResponseEntity<Object> handleInvalidMimeTypeException(final IllegalArgumentException ex, final WebRequest request) {
        return handleExceptionInternal(ex, this.createMessageError("unsupported.media.type", this.messageDevCheked(ex.getLocalizedMessage())), new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE, request);
    }

    /**
     * Erro: 500
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class })
    public final ResponseEntity<Object> handleNullAndIllegalException(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex, this.createMessageError("internal.server.error", this.messageDevCheked(ex.getLocalizedMessage())), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = { EmptyResultDataAccessException.class })
    public final ResponseEntity<Object> handleEmptyResultDataAccessException(final EmptyResultDataAccessException ex, final WebRequest request) {
        return handleExceptionInternal(ex, this.createMessageError("resource.not.found", this.messageDevCheked(ex.getLocalizedMessage())), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { ConstraintViolationException.class, DataIntegrityViolationException.class })
    public final ResponseEntity<Object> handleDataIntegrityViolationException(final DataIntegrityViolationException ex, final WebRequest request) {
        return handleExceptionInternal(ex, this.createMessageError("operation.not.allowed", this.messageDevCheked(ex.getLocalizedMessage())), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Mensagem de erro para o usuário e dev.
     *
     * @param messageUser
     * @param messageDev
     * @return
     */
    private List<MessageError> createMessageError(final String messageUser, final String messageDev) {
        final String messageUserLanguage = this.messageSource.getMessage(messageUser, null, LocaleContextHolder.getLocale());
        this.log.error(messageDev);
        return Arrays.asList(new MessageError(messageUserLanguage, messageDev));
    }

    /**
     * Lista de campos inválidos.
     *
     * @param bindingResult
     * @return
     */
    private List<MessageError> createFieldMessageError(final BindingResult bindingResult) {
        final List<MessageError> errors = new ArrayList<>();

        for (final FieldError fieldError : bindingResult.getFieldErrors()) {
            final String messageUser = this.messageSource.getMessage(fieldError.getDefaultMessage(), null, LocaleContextHolder.getLocale());
            final String messageDev = fieldError.toString();
            this.log.error(messageDev);
            errors.add(new MessageError(messageUser, messageDev));
        }

        return errors;
    }

    /**
     * @param localizeMessage
     * @return
     */
    private String messageDevCheked(final String localizeMessage) {
        return !StringUtils.isEmpty(localizeMessage) ? localizeMessage : "";
    }
}
