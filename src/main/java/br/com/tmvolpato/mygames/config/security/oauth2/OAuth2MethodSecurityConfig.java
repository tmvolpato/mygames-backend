package br.com.tmvolpato.mygames.config.security.oauth2;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

/**
 * Habilita o metodo oauth2 para uso do escopo.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @sice 2018
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return new OAuth2MethodSecurityExpressionHandler();
    }
}