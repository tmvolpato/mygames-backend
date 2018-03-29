package br.com.tmvolpato.mygames.repository.game;

import br.com.tmvolpato.mygames.model.Game;
import br.com.tmvolpato.mygames.repository.game.queryCustom.GameRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repositório do game.
 *
 * @author Thiago Michel Volpato
 * @sice 2017
 * @version 1.0.0
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Long>, JpaSpecificationExecutor<Game>, GameRepositoryQuery {
}
