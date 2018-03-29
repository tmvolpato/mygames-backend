package br.com.tmvolpato.mygames.service.User;

import br.com.tmvolpato.mygames.model.User;
import br.com.tmvolpato.mygames.service.IRawService;

import java.util.Optional;

/**
 * Interface serviço usuário.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
public interface UserService extends IRawService<User> {

    Optional<User> findById(Long id);
    Optional<User> findUsername(String email);
}
