package br.com.tmvolpato.mygames.repository.game.specification;

import br.com.tmvolpato.mygames.common.constant.ConstantQuery;
import br.com.tmvolpato.mygames.model.*;
import br.com.tmvolpato.mygames.service.security.UserApplication;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

/**
 * Specific query for game.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
public final class GameSpecification {

    /**
     * Find by id of game.
     *
     * @param id
     * @return
     */
    public static Specification<Game> findById(final UserApplication userApplication, final Long id) {
        return ((root, criteriaQuery, criteriaBuilder) -> {

            root.fetch(Game_.company, JoinType.LEFT);
            root.fetch(Game_.platform, JoinType.LEFT);
            root.fetch(Game_.genre, JoinType.LEFT);
            root.fetch(Game_.user, JoinType.LEFT)
                    .fetch(User_.roles, JoinType.LEFT)
                    .fetch(Role_.privileges, JoinType.LEFT);
            final Expression<User> user = root.get(Game_.user);
            final Expression<Long> gameId = root.get(Game_.id);
            return criteriaBuilder.and(criteriaBuilder.equal(user, userApplication.getUser()),
                                criteriaBuilder.equal(gameId, id));
        });
    }

    /**
     * Find by title of game.
     *
     * @param title
     * @return
     */
    public static Specification<Game> findByTitle(final String title) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            final Expression<String> property = root.get(Game_.title);
            return criteriaBuilder.equal(property, ConstantQuery.PERCENTAGE + title + ConstantQuery.PERCENTAGE);
        });
    }

    /**
     * Find by platform of game.
     *
     * @param platform
     * @return
     */
    public static Specification<Game> findByPlatform(final Platform platform) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            final Join<Game, Platform> platformJoin = root.join(Game_.platform);
            return criteriaBuilder.equal(platformJoin, platform);
        });
    }

    /**
     * Find by company of game.
     *
     * @param company
     * @return
     */
    public static Specification<Game> findByCompany(final Company company) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            final Join<Game, Company> companyJoin = root.join(Game_.company);
            return criteriaBuilder.equal(companyJoin, company);
        });
    }

    /**
     * Find by genre of game.
     *
     * @param genre
     * @return
     */
    public static Specification<Game> findByGenre(final Genre genre) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            final Join<Game, Genre> genreJoin = root.join(Game_.genre);
            return criteriaBuilder.equal(genreJoin, genre);
        });
    }
}
