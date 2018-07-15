package br.com.tmvolpato.mygames.common.web.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Receives the error of class MyGamesExceptionHandler.
 *
 * @author Thiago Michel
 * @since 2018
 * @version 1.0.0
 */
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
@JsonTypeIdResolver(LowerCaseClassNameResolver.class)
public class MessageApiError {

    @Getter
    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @Getter
    private LocalDateTime timestamp;

    @Getter
    @Setter
    private String message;

    @Getter
    private String debugMessage;

    @Getter
    private List<MessageSubError> messageSubErrors;

    private MessageApiError() {
        this.timestamp = LocalDateTime.now();
    }

    MessageApiError(final HttpStatus status) {
        this();
        this.status = status;
    }

    MessageApiError(final HttpStatus status, final Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    MessageApiError(final HttpStatus status, final String message, final Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    private void addMessageSubError(final MessageSubError messageSubError) {
        if (this.messageSubErrors == null) {
            this.messageSubErrors = new ArrayList<>();
        }
        this.messageSubErrors.add(messageSubError);
    }

    private void addValidationError(final String object, final String field, final Object rejectedValue, final String message) {
        this.addMessageSubError(new MessageApiValidationError(object, field, rejectedValue, message));
    }

    private void addValidationError(final String object, final String message) {
        this.addMessageSubError(new MessageApiValidationError(object, message));
    }

    private void addValidationError(final FieldError fieldError) {
        this.addValidationError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }

    void addValidationErrors(final List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    private void addValidationError(final ObjectError objectError) {
        this.addValidationError(
                objectError.getObjectName(),
                objectError.getDefaultMessage());
    }

    void addValidationError(final List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }

    private void addValidationError(final ConstraintViolation<?> constraintViolation) {
        this.addValidationError(
                constraintViolation.getRootBeanClass().getSimpleName(),
                ((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().asString(),
                constraintViolation.getInvalidValue(),
                constraintViolation.getMessage());
    }

    void addValidationErrors(final Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }
}


abstract class MessageSubError {
}

@EqualsAndHashCode(callSuper = false)
class MessageApiValidationError extends MessageSubError {

    @Getter
    private String object;

    @Getter
    private String field;

    @Getter
    private Object rejectedValue;

    @Getter
    private String message;

    protected MessageApiValidationError() {
    }

    MessageApiValidationError(final String object, final String message) {
        this.object = object;
        this.message = message;
    }

    MessageApiValidationError(final String object, final String field, final Object rejectedValue, final String message) {
        this.object = object;
        this.field = field;
        this.object = object;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }
}

class LowerCaseClassNameResolver extends TypeIdResolverBase {

    @Override
    public String idFromValue(final Object value) {
        return value.getClass().getSimpleName().toLowerCase();
    }

    @Override
    public String idFromValueAndType(final Object value, final Class<?> suggestedType) {
        return idFromValue(value);
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.CUSTOM;
    }
}
