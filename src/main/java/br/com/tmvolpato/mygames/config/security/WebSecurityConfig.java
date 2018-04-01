package br.com.tmvolpato.mygames.config.security;

import br.com.tmvolpato.mygames.service.security.BSPasswordEncoder;
import br.com.tmvolpato.mygames.service.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * Segurança da aplicação
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @sice 2017
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.signing-key}")
    private String signingKey;

    @Value("${security.security-realm}")
    private String securityRealm;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationEventPublisher authenticationEventPublisher;

    @Autowired
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth
                //.eraseCredentials(false)
                //.authenticationEventPublisher(this.authenticationEventPublisher)
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(this.passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
                .realmName(this.securityRealm)
                .and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint((request, response, authException) ->
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED));
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        final String idForEncode = "bcrypt";
        final BCryptPasswordEncoder defaultEncoder = new BCryptPasswordEncoder();

        final Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idForEncode, defaultEncoder);

        final DelegatingPasswordEncoder rv = new DelegatingPasswordEncoder(idForEncode, encoders);
        rv.setDefaultPasswordEncoderForMatches(new BSPasswordEncoder());
        return rv;
    }

//    @Bean
//    public AuthenticationEventPublisher authenticationEventPublisher(final ApplicationEventPublisher publisher) {
//        return new DefaultAuthenticationEventPublisher(publisher);
//    }
//
//    @Bean
//    public ApplicationListener<AuthenticationSuccessEvent> onSuccessListener(final PasswordEncoder passwordEncoder) {
//        return (AuthenticationSuccessEvent event) -> {
//            final Authentication authentication = event.getAuthentication();
//
//            if (authentication instanceof UsernamePasswordAuthenticationToken && authentication.getCredentials() != null) {
//
////                final CharSequence plainTextPassword = (CharSequence) authentication.getCredentials();
////                final String rehashedPassword = passwordEncoder.encode(plainTextPassword);
////                System.out.print(rehashedPassword);
////                ((UsernamePasswordAuthenticationToken) authentication).eraseCredentials();
//            }
//        };
//
//    }
}

