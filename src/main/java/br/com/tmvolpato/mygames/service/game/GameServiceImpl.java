package br.com.tmvolpato.mygames.service.game;

import br.com.tmvolpato.mygames.model.Game;
import br.com.tmvolpato.mygames.model.User;
import br.com.tmvolpato.mygames.repository.game.GameRepository;
import br.com.tmvolpato.mygames.repository.game.filter.GameFilter;
import br.com.tmvolpato.mygames.repository.game.specification.GameSpecification;
import br.com.tmvolpato.mygames.service.ServicePreconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implementação do serviço do game.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @since 2017
 */
@Service
@Transactional
public class GameServiceImpl implements GameService {

    /**
     * Repositório do game.
     */
    private final GameRepository gameRepository;

    @Autowired
    public GameServiceImpl(final GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Game create(final User userLogged, final Game game) {
        ServicePreconditions.checkEntityExists(userLogged);
        ServicePreconditions.checkEntityExists(game);
        game.setUser(userLogged);
        return this.gameRepository.save(game);
    }

    @Override
    public Game update(final User userLogged, final Game game) {
        ServicePreconditions.checkEntityExists(userLogged);
        ServicePreconditions.checkEntityExists(game);
        ServicePreconditions.checkResourceOwner(userLogged.getId(), game.getUser().getId());
        return this.gameRepository.saveAndFlush(game);
    }

    @Override
    public void delete(final User userLogged, final Long primaryKey) {
        final Optional<Game> game = this.findById(userLogged, primaryKey);
        this.gameRepository.delete(game.get());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Game> findById(final User userLogged, final Long id) {
        ServicePreconditions.checkEntityExists(userLogged);
        ServicePreconditions.checkParameterLong(id);
        final Optional<Game> game = this.gameRepository.findOne(Specification.where(GameSpecification.findById(userLogged, id)));
        ServicePreconditions.checkEntityExists(game.isPresent());
        return game;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Game> findAllPaginatedAndFilter(final User userLogged, final GameFilter gameFilter,
                                                final int page, final int size) {
        ServicePreconditions.checkEntityExists(userLogged);
        return this.gameRepository.findAllPaginatedAndFilter(userLogged, gameFilter, page, size);
    }
}
