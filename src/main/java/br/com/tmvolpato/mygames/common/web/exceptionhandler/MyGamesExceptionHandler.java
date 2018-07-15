package br.com.tmvolpato.mygames.common.web.exceptionhandler;

import br.com.tmvolpato.mygames.common.constant.ConstantNumeric;
import br.com.tmvolpato.mygames.common.web.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

/**
 * Exception handler of application.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @since 2017
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class MyGamesExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Hnadle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected final ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex,
                                                                                final HttpHeaders headers, final HttpStatus status,
                                                                                final WebRequest request) {
        final String error = ex.getParameterName() + " parameter is missing";
        return this.buildResponseEntity(new MessageApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    /**
     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected final ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
                                                                           final HttpHeaders headers,
                                                                           final HttpStatus status,
                                                                           final WebRequest request) {
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(type -> builder.append(type).append(", "));
        return this.buildResponseEntity(new MessageApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder.substring(ConstantNumeric.ZERO, builder.length() - ConstantNumeric.TWO), ex));
    }

    /**
     *
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected final ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
                                                                        final HttpHeaders headers,
                                                                        final HttpStatus status,
                                                                        final WebRequest request) {
        final ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        this.log.info("{} to {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath());
        final String error = "Malformed JSON request";
        return this.buildResponseEntity(new MessageApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    /**
     * Handle MethodArgumentNotValid. Triggered when an object fails @Valid validation.
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    public final ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                        final HttpHeaders headers,
                                                                        final HttpStatus status,
                                                                        final WebRequest request) {
        final MessageApiError messageApiError = new MessageApiError(HttpStatus.BAD_REQUEST);
        messageApiError.setMessage("Validation error");
        messageApiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        messageApiError.addValidationError(ex.getBindingResult().getGlobalErrors());
        return this.buildResponseEntity(messageApiError);
    }

    /**
     * Handles javax.validation.ConstraintViolationException. Throw when @Validated fails.
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = { ConstraintViolationException.class, MyBadRequestException.class })
    public final ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex) {
        final MessageApiError messageApiError = new MessageApiError(HttpStatus.BAD_REQUEST);
        messageApiError.setMessage("Validation Error");
        messageApiError.addValidationErrors(ex.getConstraintViolations());
        return this.buildResponseEntity(messageApiError);
    }

    /**
     * Handles EntityNotFoundException. Created to encapsulate errors with more detail than javax.persistence.EntityNotFoundException.
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = { EntityNotFoundException.class, MyEntityNotFoundException.class, MyResourceNotFoundException.class })
    public ResponseEntity<Object> handleEntityNotFound(final RuntimeException ex) {
        return this.buildResponseEntity(new MessageApiError(HttpStatus.NOT_FOUND, ex));
       //final MessageApiError messageApiError = new MessageApiError(HttpStatus.NOT_FOUND);
       //messageApiError.setMessage(ex.getMessage());
       //return this.buildResponseEntity(messageApiError);
    }

    /**
     * Handle HttpMessageNotWritableException.
     *
     * @param ex      HttpMessageNotWritableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(final HttpMessageNotWritableException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {
        final String error = "Error writing JSON output";
        return buildResponseEntity(new MessageApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
    }

    /**
     * Handle DataIntegrityViolationException, inspects the cause for different DB causes.
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { DataIntegrityViolationException.class, MyEmailExistException.class })
    public final ResponseEntity<Object> handleDataIntegrityViolation(final DataIntegrityViolationException ex, final WebRequest request) {
      if (ex.getCause() instanceof ConstraintViolationException) {
          this.logger.error("Data Integrity Violation", ex.getCause());
          return this.buildResponseEntity(new MessageApiError(HttpStatus.CONFLICT, "Database error", ex.getCause()));
      }

      if (ex.getCause() != null && ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
          this.logger.error("Data Integrity Violation", ex);
          return this.buildResponseEntity(new MessageApiError(HttpStatus.CONFLICT, "Database error", ex));
      }

        this.logger.error("Data Integrity", ex);
          return this.buildResponseEntity(new MessageApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex));
    }

    /**
     * Handle AuthenticationCredentialsNotFoundException. User or password is incorrect.
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public final ResponseEntity<Object> handleAuthenticationCredentialsNotFound(final AuthenticationException ex, final WebRequest request) {
        final String error = "Authentication is required.";
        return this.buildResponseEntity(new MessageApiError(HttpStatus.FORBIDDEN, error, ex));
    }

    /**
     * Handle IllegalArgumentException or IllegalStateException.
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class, MyConflictException.class})
    public final ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
        final String error = "This should be application specific";
        return this.buildResponseEntity(new MessageApiError(HttpStatus.CONFLICT, error, ex));
    }

    /**
     * Handle AccessDeniedException. No contain permission to access the resource.
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({AccessDeniedException.class, MyAccessDeniedException.class})
    public final ResponseEntity<Object> handleAccessDenied(final Exception ex, final WebRequest request) {
        final String error = "Access denied";
        return this.buildResponseEntity(new MessageApiError(HttpStatus.FORBIDDEN, error, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(final MessageApiError messageApiError) {
        return new ResponseEntity<>(messageApiError, messageApiError.getStatus());
    }
}
