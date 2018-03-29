package br.com.tmvolpato.mygames.common.web.listener;

import br.com.tmvolpato.mygames.common.constant.ConstantNumeric;
import br.com.tmvolpato.mygames.common.constant.ConstantWeb;
import br.com.tmvolpato.mygames.common.web.event.PaginatedResultsRetrievedEvent;
import br.com.tmvolpato.mygames.common.web.mapper.IUriMapper;
import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

import static br.com.tmvolpato.mygames.common.web.util.LinkUtil.*;

/**
 * Classe listener que observa o evento de paginação e adiciona informações no cabeçalho.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
@Component
public class PaginatedResultsRetrievedDiscoverabilityListener implements ApplicationListener<PaginatedResultsRetrievedEvent> {

    private static final String PAGE = "page";
    private static final String SIZE = "size";

    @Autowired
    private IUriMapper uriMapper;

    @Override
    public void onApplicationEvent(final PaginatedResultsRetrievedEvent ev) {
        Preconditions.checkNotNull(ev);
        this.addLinkHeaderOnPageResourceRetrieval(ev.getUriBuilder(), ev.getResponse(), ev.getClazz(), ev.getPage(), ev.getTotalPages(), ev.getPageSize());
    }

    final void addLinkHeaderOnPageResourceRetrieval(final UriComponentsBuilder uriBuilder, final HttpServletResponse response,
                                                    final Class clazz, final int page, final int totalPages, final int pageSize) {

        this.plural(uriBuilder, clazz);

        final StringBuilder linkHeader = new StringBuilder();
        if (hasNextPage(page, totalPages)) {
            final String uriForNextPage = this.constructNextPageUri(uriBuilder, page, pageSize);
            linkHeader.append(createLinkHeader(uriForNextPage, REL_NEXT));
        }

        if (hasPreviousPage(page)) {
            final String uriForPrevPage = this.constructPrevPageUri(uriBuilder, page, pageSize);
            this.appendCommaIfNecessary(linkHeader);
            linkHeader.append(createLinkHeader(uriForPrevPage, REL_PREV));
        }

        if (hasFirstPage(page)) {
            final String uriForFirstPage = this.constructFirstPageUri(uriBuilder, pageSize);
            this.appendCommaIfNecessary(linkHeader);
            linkHeader.append(createLinkHeader(uriForFirstPage, REL_FIRST));
        }

        if (hasLastPage(page, totalPages)) {
            final String uriForLastPage = this.constructLastPageUri(uriBuilder, totalPages, pageSize);
            this.appendCommaIfNecessary(linkHeader);
            linkHeader.append(createLinkHeader(uriForLastPage, REL_LAST));
        }

        if (linkHeader.length() > ConstantNumeric.ZERO) {
            response.addHeader(HttpHeaders.LINK, linkHeader.toString());
        }

    }

    final String constructNextPageUri(final UriComponentsBuilder uriBuilder, final int page, final int size) {
        return uriBuilder.replaceQueryParam(PAGE, page + ConstantNumeric.ONE).replaceQueryParam(SIZE, size).build().encode().toUriString();
    }

    final String constructPrevPageUri(final UriComponentsBuilder uriBuilder, final int page, final int size) {
        return uriBuilder.replaceQueryParam(PAGE, page - ConstantNumeric.ONE).replaceQueryParam(SIZE, size).build().encode().toUriString();
    }

    final String constructFirstPageUri(final UriComponentsBuilder uriBuilder, final int size) {
        return uriBuilder.replaceQueryParam(PAGE, ConstantNumeric.ZERO).replaceQueryParam(SIZE, size).build().encode().toUriString();
    }

    final String constructLastPageUri(final UriComponentsBuilder uriBuilder, final int totalPages, final int size) {
        return uriBuilder.replaceQueryParam(PAGE, totalPages).replaceQueryParam(SIZE, size).build().encode().toUriString();
    }

    final boolean hasNextPage(final int page, final int totalPages) {
        return page < totalPages - ConstantNumeric.ONE;
    }

    final boolean hasPreviousPage(final int page) {
        return page > ConstantNumeric.ZERO;
    }

    final boolean hasFirstPage(final int page) {
        return this.hasPreviousPage(page);
    }

    final boolean hasLastPage(final int page, final int totalPages) {
        return totalPages > ConstantNumeric.ONE && this.hasNextPage(page, totalPages);
    }

    final void appendCommaIfNecessary(final StringBuilder linkHeader) {
        if (linkHeader.length() > ConstantNumeric.ZERO) {
            linkHeader.append(", ");
        }
    }

    protected void plural(final UriComponentsBuilder uriBuilder, final Class clazz) {
        final String resourceName = this.uriMapper.getUriBase(clazz);
        uriBuilder.path(ConstantWeb.SLASH + resourceName);
    }
}
