package br.com.tmvolpato.mygames.service.game;

import br.com.tmvolpato.mygames.model.Game;
import br.com.tmvolpato.mygames.repository.game.GameRepository;
import br.com.tmvolpato.mygames.repository.game.filter.GameFilter;
import br.com.tmvolpato.mygames.repository.game.specification.GameSpecification;
import br.com.tmvolpato.mygames.service.ServicePreconditions;
import br.com.tmvolpato.mygames.service.security.UserApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implementation service of game.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
@Service
@Transactional
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameServiceImpl(final GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Game create(final UserApplication userApplication, final Game game) {
        ServicePreconditions.checkUserApplication(userApplication);
        ServicePreconditions.checkEntityExists(game);
        game.setUser(userApplication.getUser());
        return this.gameRepository.save(game);
    }

    @Override
    public Game update(final UserApplication userApplication, final Game game) {
        ServicePreconditions.checkUserApplication(userApplication);
        ServicePreconditions.checkEntityExists(game);
        ServicePreconditions.checkResourceOwner(userApplication.getId(), game.getUser().getId());
        return this.gameRepository.saveAndFlush(game);
    }

    @Override
    public void delete(final UserApplication userApplication, final Long primaryKey) {
        final Optional<Game> game = this.findById(userApplication, primaryKey);
        this.gameRepository.delete(game.get());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Game> findById(final UserApplication userApplication, final Long id) {
        ServicePreconditions.checkUserApplication(userApplication);
        ServicePreconditions.checkParameterLong(id);
        final Optional<Game> game = this.gameRepository.findOne(Specification.where(GameSpecification.findById(userApplication, id)));
        ServicePreconditions.checkEntityExists(game.isPresent());
        return game;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Game> findAllPaginatedAndFilter(final UserApplication userApplication, final GameFilter gameFilter,
                                                final int page, final int size) {
        ServicePreconditions.checkUserApplication(userApplication);
        return this.gameRepository.findAllPaginatedAndFilter(userApplication, gameFilter, page, size);
    }
}
