package br.com.tmvolpato.mygames.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.stereotype.Component;

/**
 * An object mapper that registers a hibernate module, so a JSON serialization
 * doesn't fail with hibernate lazy-loading.
 *
 * @author Thiago Michel Volpato
 * @sice 2018
 * @version 1.0.0
 */
@Component
public class HibernateAwareObjectMapper extends ObjectMapper {

    public HibernateAwareObjectMapper() {
        registerModule(new Hibernate5Module());
    }
}
