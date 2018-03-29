package br.com.tmvolpato.mygames.common.web.listener;

import org.springframework.stereotype.Component;

/**
 * Classe listener de criação de um novo recurso.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
@Component
public class SecResourceCreatedDiscoverabilityListener extends ResourceCreateDiscoverabilityListener {

    public SecResourceCreatedDiscoverabilityListener() {
        super();
    }

    @Override
    protected final String getBase() {
        return "/";
    }
}
