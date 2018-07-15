package br.com.tmvolpato.mygames.common.web.listener;

import br.com.tmvolpato.mygames.common.constant.ConstantWeb;
import org.springframework.stereotype.Component;

/**
 * Listening the event for created of a new resource.
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
        return ConstantWeb.SLASH;
    }
}
