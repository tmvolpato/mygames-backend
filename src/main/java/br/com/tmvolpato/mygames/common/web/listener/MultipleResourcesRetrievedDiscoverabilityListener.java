package br.com.tmvolpato.mygames.common.web.listener;

import br.com.tmvolpato.mygames.common.constant.ConstantWeb;
import br.com.tmvolpato.mygames.common.web.event.MultipleResourcesRetrievedEvent;
import br.com.tmvolpato.mygames.common.web.mapper.IUriMapper;
import br.com.tmvolpato.mygames.common.web.util.LinkUtil;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

/**
 * Classe listener que observa o evento de multiplos recursos e adiciona informações no cabeçalho.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
@Component
public final class MultipleResourcesRetrievedDiscoverabilityListener implements ApplicationListener<MultipleResourcesRetrievedEvent> {

    @Autowired
    private IUriMapper uriMapper;

    @Override
    public void onApplicationEvent(final MultipleResourcesRetrievedEvent ev) {
        Preconditions.checkNotNull(ev);
        this.discoverOtherRetrievalOperations(ev.getUriBuilder(), ev.getResponse(), ev.getClazz());
    }

    final void discoverOtherRetrievalOperations(final UriComponentsBuilder uriBuilder, final HttpServletResponse response, final Class clazz) {
        final String uriForResourceCreation = uriBuilder.path(ConstantWeb.API + uriMapper.getUriBase(clazz) + "/q=name=something").build().toUriString();

        final String linkHeaderValue = LinkUtil.createLinkHeader(uriForResourceCreation, LinkUtil.REL_COLLECTION);
        response.addHeader(HttpHeaders.LINK, linkHeaderValue);
    }
}
