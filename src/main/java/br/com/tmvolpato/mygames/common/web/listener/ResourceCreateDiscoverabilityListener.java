package br.com.tmvolpato.mygames.common.web.listener;

import br.com.tmvolpato.mygames.common.constant.ConstantWeb;
import br.com.tmvolpato.mygames.common.web.event.AfterResourceCreateEvent;
import br.com.tmvolpato.mygames.common.web.mapper.IUriMapper;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

/**
 * Classe ouvinte que dispara o evento de criação da uri para o novo recurso criado.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
public abstract class ResourceCreateDiscoverabilityListener implements ApplicationListener<AfterResourceCreateEvent> {

    @Autowired
    private IUriMapper uriMapper;

    public ResourceCreateDiscoverabilityListener() {
        super();
    }

    @Override
    public void onApplicationEvent(final AfterResourceCreateEvent ev) {

        Preconditions.checkNotNull(ev);
        final String primaryKeyOfNewResource = ev.getPrimaryKeyOfNewResource();
        this.addLinkHeaderOnEntityCreation(ev.getUriBuilder(), ev.getResponse(), primaryKeyOfNewResource, ev.getClazz());
    }

    private void addLinkHeaderOnEntityCreation(final UriComponentsBuilder uriBuilder, final HttpServletResponse response,
                                                 final String idOfNewEntity, final Class clazz) {
        final String path = this.buildPathResource(clazz);
        final String locationValue = uriBuilder.path(path).build().expand(idOfNewEntity).encode().toUriString();
        response.setHeader(HttpHeaders.LOCATION, locationValue);

    }

    private String buildPathResource(final Class clazz) {
        final String resourceName = this.uriMapper.getUriBase(clazz);
        return ConstantWeb.SLASH + resourceName + "/{id}";
    }

    protected abstract String getBase();
}
