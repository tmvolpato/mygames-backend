package br.com.tmvolpato.mygames.resource;

import br.com.tmvolpato.mygames.common.constant.ConstantRegexp;
import br.com.tmvolpato.mygames.common.interfaces.IEntity;
import br.com.tmvolpato.mygames.common.web.RestPreconditions;
import br.com.tmvolpato.mygames.common.web.event.AfterResourceCreateEvent;
import br.com.tmvolpato.mygames.common.web.event.MultipleResourcesRetrievedEvent;
import br.com.tmvolpato.mygames.common.web.event.PaginatedResultsRetrievedEvent;
import br.com.tmvolpato.mygames.common.web.event.SingleResourceRetrievedEvent;
import br.com.tmvolpato.mygames.common.web.exception.MyBadRequestException;
import br.com.tmvolpato.mygames.common.web.exception.MyResourceNotFoundException;
import br.com.tmvolpato.mygames.service.ServicePreconditions;
import br.com.tmvolpato.mygames.service.security.UserApplication;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

/**
 * Abstract class to the resources.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
public abstract class AbstractResource<T extends IEntity> {

    private Pattern pattern = Pattern.compile(ConstantRegexp.ALFABETIC_AND_NUMERIC_PATTERN);

    protected Class<T> clazz;

    @Autowired
    protected ApplicationEventPublisher eventPublisher;

    public AbstractResource(final Class<T> clazzToSet) {
        super();

        ServicePreconditions.checkEntityExists(clazzToSet);
        this.clazz = clazzToSet;
    }

    protected final void checkRequiredCreateInternal(final T resource) {
        RestPreconditions.checkRequestElementNotNull(resource);
    }

    protected final void checkRequiredUpdateInternal(final Long id, final T resource) {
        RestPreconditions.checkRequestElementNotNull(resource);
        RestPreconditions.checkRequestElementNotNull(resource.getId());
        RestPreconditions.checkNotNull(resource.getId().equals(id));
    }

    protected final void checkUserApplication(final UserApplication userApplication) {
        RestPreconditions.checkNotNull(userApplication);
    }

    protected final void checkRequiredSingleResourceInternal(final T resource) {
        RestPreconditions.checkNotNull(resource);
    }

    protected final void checkRequiredSingleResourceInternal(final boolean exists) {
        RestPreconditions.checkNotNull(exists);
    }

    protected final void checkRequiredPaginatedResourceInternal(final int page, final Page<?> resultPage) {
        if (page > resultPage.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }
    }

    protected final void checkRequiredQueryString(final String queryString) {
        try {
            Preconditions.checkNotNull(queryString);
            Preconditions.checkState(this.pattern.matcher(queryString).matches());

        } catch (final IllegalStateException e) {
            throw new MyBadRequestException(e);
        }
    }

    protected final void checkRequiredFindAllInternal(final HttpServletRequest request) {
        if (request.getParameterNames().hasMoreElements()) {
            throw new MyResourceNotFoundException();
        }
    }

    protected final void checkRequiredPrimaryKeyDeleteInternal(final Long id) {
        Preconditions.checkNotNull(id);
    }

    protected void multiPublishEvent(final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        this.eventPublisher.publishEvent(new MultipleResourcesRetrievedEvent<>(this.clazz, uriBuilder, response));
    }

    protected void createPublishEvent(final T savedResource, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        this.eventPublisher.publishEvent(new AfterResourceCreateEvent<>(this.clazz, savedResource.getId().toString(), uriBuilder, response));
    }

    protected void singlePublishEvent(final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        this.eventPublisher.publishEvent(new SingleResourceRetrievedEvent<>(this.clazz, uriBuilder, response));
    }

    protected void paginatedPublishEvent(final int page, final int pageSize, final int totalPages, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        this.eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(this.clazz, uriBuilder, response, page, totalPages, pageSize));
    }

    protected String getPrincipal() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null || authentication.getPrincipal().equals("") ) {
            //throw new MyUnauthorizedException();

        }
        return authentication.getPrincipal().toString();
    }
}
