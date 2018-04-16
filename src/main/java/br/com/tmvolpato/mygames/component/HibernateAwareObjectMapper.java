package br.com.tmvolpato.mygames.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.stereotype.Component;

/**
 * Um object mapper que registra um hibernate modulo, portanto, uma serialização JSON
 * não falha com o carregamento lento do hibernate.

 *
 * An object mapper that registers a hibernate module, so a JSON serialization
 * doesn't fail with hibernate lazy-loading.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @sice 2018
 */
@Component
public class HibernateAwareObjectMapper extends ObjectMapper {

    public HibernateAwareObjectMapper() {
        registerModule(new Hibernate5Module());
    }
}
