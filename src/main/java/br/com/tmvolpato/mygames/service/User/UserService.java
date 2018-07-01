package br.com.tmvolpato.mygames.service.User;

import br.com.tmvolpato.mygames.model.User;
import br.com.tmvolpato.mygames.service.IRawService;
import br.com.tmvolpato.mygames.service.security.UserApplication;

import java.util.Optional;

/**
 * Interface user service.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
public interface UserService extends IRawService<UserApplication, User> {

    Optional<User> findById(UserApplication userApplication, Long id);
    Optional<User> findUsername(String email);
}
