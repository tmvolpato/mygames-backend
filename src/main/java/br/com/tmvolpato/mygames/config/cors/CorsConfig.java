package br.com.tmvolpato.condomanager.config.cors;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Classe de configuração do Cors filter.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @sice 2017
 */
@Profile("dev")
@Configuration
public class CorsConfig {

    //private String allowOrigin = "http://localhost:8000";

    @Bean
    public FilterRegistrationBean customCorsFilter() {
        final UrlBasedCorsConfigurationSource urlBaseCors = new UrlBasedCorsConfigurationSource();

        final CorsConfiguration configAuthentication = new CorsConfiguration();
        configAuthentication.setAllowCredentials(true);

        configAuthentication.addAllowedOrigin("*");

        configAuthentication.addAllowedHeader(ConstantCorsHeader.ORIGIN);
        configAuthentication.addAllowedHeader(ConstantCorsHeader.AUTHORIZATION);
        configAuthentication.addAllowedHeader(ConstantCorsHeader.X_REQUESTED_WITH);
        configAuthentication.addAllowedHeader(ConstantCorsHeader.CONTENT_TYPE);
        configAuthentication.addAllowedHeader(ConstantCorsHeader.ACCEPT);
        configAuthentication.addAllowedHeader(ConstantCorsHeader.ACCEPT_ENCODING);
        configAuthentication.addAllowedHeader(ConstantCorsHeader.ACCEPT_LANGUAGE);
        configAuthentication.addAllowedHeader(ConstantCorsHeader.HOST);
        configAuthentication.addAllowedHeader(ConstantCorsHeader.REFER);
        configAuthentication.addAllowedHeader(ConstantCorsHeader.CONNECTION);
        configAuthentication.addAllowedHeader(ConstantCorsHeader.USER_AGENT);

        configAuthentication.addAllowedMethod(HttpMethod.POST);
        configAuthentication.addAllowedMethod(HttpMethod.GET);
        configAuthentication.addAllowedMethod(HttpMethod.DELETE);
        configAuthentication.addAllowedMethod(HttpMethod.PUT);
        configAuthentication.addAllowedMethod(HttpMethod.HEAD);
        configAuthentication.addAllowedMethod(HttpMethod.PATCH);

        configAuthentication.setMaxAge(3600L);

        urlBaseCors.registerCorsConfiguration("/**", configAuthentication);

        final FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new CorsFilter(urlBaseCors));
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterRegistrationBean;
    }

    /**
     * Informações passadas no cabeçalho (Header).
     */
    static class ConstantCorsHeader {

        private static final String ORIGIN = "Origin";
        private static final String AUTHORIZATION = "Authorization";
        private static final String X_REQUESTED_WITH = "X-Requested-with";
        private static final String CONTENT_TYPE = "Content-Type";
        private static final String ACCEPT = "Accept";
        private static final String ACCEPT_ENCODING = "Accept-Encoding";
        private static final String ACCEPT_LANGUAGE = "Accept-Language";
        private static final String HOST = "Host";
        private static final String REFER = "Refer";
        private static final String CONNECTION = "Connection";
        private static final String USER_AGENT = "User-Agent";

        private ConstantCorsHeader() {
            throw new AssertionError();
        }
    }
}