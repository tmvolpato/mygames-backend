package br.com.tmvolpato.mygames.config.security.oauth2;

import br.com.tmvolpato.mygames.config.security.token.CustomTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

/**
 * Authorization server in memory.
 *
 * @author Thiago Michel Volpato
 * @sice 2017
 * @version 1.0.0
 */
//@Configuration
//@EnableAuthorizationServer
public class OAuth2AuthorizationServerInMemoryConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomTokenEnhancer tokenEnhancer;

    @Value("${security.jwt.client-id}")
    private String clientId;

    @Value("${security.jwt.client-secret}")
    private String clientSecret;

    @Value("${security.jwt.grant-type-password}")
    private String grantTypePassword;

    @Value("${security.jwt.grant-type-refreshtoken}")
    private String grantTypeRefreshToken;

    @Value("${security.jwt.scope-read}")
    private String scopeRead;

    @Value("${security.jwt.scope-write}")
    private String scopeWrite;

    @Value("${security.jwt.resource-ids}")
    private String resourceIds;

    @Value("${security.signing-key}")
    private String signingKey;

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(this.clientId)
                .secret(this.clientSecret)
                .scopes(this.scopeRead, this.scopeWrite)
                .authorizedGrantTypes(this.grantTypePassword, this.grantTypeRefreshToken)
                .resourceIds(this.resourceIds);
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(this.tokenEnhancer, this.accessTokenConverter()));

        endpoints
                .authenticationManager(this.authenticationManager)
                .tokenStore(this.jwtTokenStore())
                .tokenEnhancer(tokenEnhancerChain)
                .accessTokenConverter(this.accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(this.signingKey);
        return converter;
    }

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(this.accessTokenConverter());
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(this.jwtTokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }
}


