package br.com.tmvolpato.mygames.common.web.event;

import br.com.tmvolpato.mygames.common.interfaces.IWithLongId;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

/**
 * Evento disparado para paginação.
 *
 * Contém todas as informações necessárias da URL de resultados da paginação
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
public class PaginatedResultsRetrievedEvent<T extends IWithLongId> extends ApplicationEvent {

    @Getter
    private final transient UriComponentsBuilder uriBuilder;

    @Getter
    private final transient HttpServletResponse response;

    @Getter
    private final int page;

    @Getter
    private final int totalPages;

    @Getter
    private final int pageSize;

    public PaginatedResultsRetrievedEvent(final Class<T> clazz, final UriComponentsBuilder uriBuilder,
                                          final HttpServletResponse response, final int page,
                                          final int totalPages, final int pageSize) {
        super(clazz);

        this.uriBuilder = uriBuilder;
        this.response = response;
        this.page = page;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
    }

    public final Class<T> getClazz() {
        return (Class<T>) getSource();
    }
}
