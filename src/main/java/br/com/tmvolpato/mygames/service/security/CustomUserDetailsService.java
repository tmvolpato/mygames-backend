package br.com.tmvolpato.mygames.service.security;

import br.com.tmvolpato.mygames.model.User;
import br.com.tmvolpato.mygames.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Classe customizada para detalhe do usuário logado.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @sice 2017
 */
@Primary
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public CustomUserDetailsService(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final Optional<User> userFound = this.userService.findUsername(email);
        if (userFound.isPresent()) {
            return new UserApplication(userFound.get());
        } else {
            throw new UsernameNotFoundException("usuario não autorizado");
        }
    }
}
