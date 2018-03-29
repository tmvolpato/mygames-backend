package br.com.tmvolpato.mygames.common.web.event;

import br.com.tmvolpato.mygames.common.interfaces.IEntity;
import com.google.common.base.Preconditions;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

/**
 * Evento disparado quando o recurso é criado.
 *
 * Cria a url que direciona para o novo recurso criado.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @sice 2017
 */
public final class AfterResourceCreateEvent<T extends IEntity> extends ApplicationEvent {

    @Getter
    private final String primaryKeyOfNewResource;

    @Getter
    private final transient UriComponentsBuilder uriBuilder;

    @Getter
    private final transient HttpServletResponse response;

    public AfterResourceCreateEvent(final Class<T> clazz, final String primaryKeyOfNewResource,
                                    final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        super(clazz);

        Preconditions.checkNotNull(primaryKeyOfNewResource);
        Preconditions.checkNotNull(response);
        Preconditions.checkNotNull(uriBuilder);

        this.primaryKeyOfNewResource = primaryKeyOfNewResource;
        this.response = response;
        this.uriBuilder = uriBuilder;
    }

    public final Class<T> getClazz() {
        return (Class<T>) getSource();
    }
}
