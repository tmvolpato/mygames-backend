package br.com.tmvolpato.mygames.common.web.listener;

import br.com.tmvolpato.mygames.common.constant.ConstantWeb;
import br.com.tmvolpato.mygames.common.web.event.SingleResourceRetrievedEvent;
import br.com.tmvolpato.mygames.common.web.mapper.IUriMapper;
import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

import static br.com.tmvolpato.mygames.common.web.util.LinkUtil.REL_COLLECTION;
import static br.com.tmvolpato.mygames.common.web.util.LinkUtil.createLinkHeader;



/**
 * Classe listener que observa o evento
 * simples quando é recuperado e adiciona informações no cabeçalho.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @since 2017
 */
@Component
public class SingleResourceRetrievedDiscoverabilityListener implements ApplicationListener<SingleResourceRetrievedEvent> {

    @Autowired
    private IUriMapper uriMapper;

    @Override
    public void onApplicationEvent(final SingleResourceRetrievedEvent ev) {
        Preconditions.checkNotNull(ev);
        this.discoverGetAllURI(ev.getUriBuilder(), ev.getResponse(), ev.getClazz());
    }

    final void discoverGetAllURI(final UriComponentsBuilder uriBuilder, final HttpServletResponse response, final Class clazz) {
        final String uriForResourceCreation = uriBuilder.path(ConstantWeb.API + uriMapper.getUriBase(clazz)).build().encode().toUriString();
        final String linkHeaderValue = createLinkHeader(uriForResourceCreation, REL_COLLECTION);
        response.addHeader(HttpHeaders.LINK, linkHeaderValue);
    }
}
