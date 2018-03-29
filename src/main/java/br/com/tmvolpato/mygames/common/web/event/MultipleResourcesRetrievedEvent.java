package br.com.tmvolpato.mygames.common.web.event;

import br.com.tmvolpato.mygames.common.interfaces.IEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

/**
 * Evento disparado quando multiplos recursos são recuperados.
 *
 * Contém todas as informações necessárias da URL que direciona para o recurso.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
public final class MultipleResourcesRetrievedEvent<T extends IEntity> extends ApplicationEvent {

    @Getter
    private final transient UriComponentsBuilder uriBuilder;

    @Getter
    private final transient HttpServletResponse response;

    public MultipleResourcesRetrievedEvent(final Class<T> clazz, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        super(clazz);

        this.uriBuilder = uriBuilder;
        this.response = response;
    }

    public final Class<T> getClazz() {
        return (Class<T>) getSource();
    }
}
