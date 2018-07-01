package br.com.tmvolpato.mygames.service.game;

import br.com.tmvolpato.mygames.model.Game;
import br.com.tmvolpato.mygames.model.User;
import br.com.tmvolpato.mygames.repository.game.filter.GameFilter;
import br.com.tmvolpato.mygames.service.IRawService;
import br.com.tmvolpato.mygames.service.security.UserApplication;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * Interface serviço game.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
public interface GameService extends IRawService<UserApplication, Game> {

    Optional<Game> findById(UserApplication userApplication, Long id);
    Page<Game> findAllPaginatedAndFilter(UserApplication userApplication, GameFilter gameFilter, int page, int size);
}
