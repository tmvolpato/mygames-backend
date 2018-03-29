package br.com.tmvolpato.mygames.repository.game.queryCustom;

import br.com.tmvolpato.mygames.model.Game;
import br.com.tmvolpato.mygames.model.User;
import br.com.tmvolpato.mygames.repository.game.filter.GameFilter;
import org.springframework.data.domain.Page;

/**
 * Interface query customizada para game.
 *
 * @author Thiago Michel Volpato
 * @sice 2017
 * @version 1.0.0
 */
public interface GameRepositoryQuery {

    Page<Game> findAllPaginatedAndFilter(User user, GameFilter gameFilter, int page, int size);
    long count(User user, GameFilter gameFilter);
}
