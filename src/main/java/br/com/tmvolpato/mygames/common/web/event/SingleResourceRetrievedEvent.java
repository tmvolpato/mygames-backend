package br.com.tmvolpato.mygames.common.web.event;

import br.com.tmvolpato.mygames.common.interfaces.IWithLongId;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

 /**
  * Evento disparado quando um simples recurso é recuperado.
  *
  * Contém todas as informações necessárias da URL que direciona para o recurso.
  *
  * @author Thiago Michel Volpato
  * @since 2017
  * @version 1.0.0
  */
public class SingleResourceRetrievedEvent<T extends IWithLongId> extends ApplicationEvent {

    @Getter
    private final transient UriComponentsBuilder uriBuilder;

    @Getter
    private final transient HttpServletResponse response;

    public SingleResourceRetrievedEvent(final Class<T> clazz, final UriComponentsBuilder uriBuilder,
                                        final HttpServletResponse response) {
        super(clazz);

        this.uriBuilder = uriBuilder;
        this.response = response;
    }

    public final Class<T> getClazz() {
        return (Class<T>) getSource();
    }
}
