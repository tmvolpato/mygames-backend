package br.com.tmvolpato.mygames.repository.game.specification;

import br.com.tmvolpato.mygames.common.constant.ConstantQuery;
import br.com.tmvolpato.mygames.model.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

/**
 * Query especifica do game.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @sice 2017
 */
public final class GameSpecification {

    /**
     * Procura pelo ID.
     *
     * @param id
     * @return
     */
    public static Specification<Game> findById(final Long id) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            final Expression<Long> property = root.get(Game_.id);
            return criteriaBuilder.equal(property, id);
        };
    }

    /**
     * Procura pelo título.
     *
     * @param title
     * @return
     */
    public static Specification<Game> findByTitle(final String title) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            final Expression<String> property = root.get(Game_.title);
            return criteriaBuilder.equal(property, ConstantQuery.PERCENTAGE + title + ConstantQuery.PERCENTAGE);
        };
    }

    /**
     * Procura pela plataforma.
     *
     * @param platform
     * @return
     */
    public static Specification<Game> findByPlatform(final Platform platform) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            final Join<Game, Platform> platformJoin = root.join(Game_.platform, JoinType.LEFT);
            return criteriaBuilder.equal(platformJoin.get(Platform_.id), platform.getId());
        };
    }

    /**
     * Procura pela empresa.
     *
     * @param company
     * @return
     */
    public static Specification<Game> findByCompany(final Company company) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            final Join<Game, Company> companyJoin = root.join(Game_.company, JoinType.LEFT);
            return criteriaBuilder.equal(companyJoin.get(Company_.id), company.getId());
        };
    }

    /**
     * Procura pelo gênero.
     *
     * @param genre
     * @return
     */
    public static Specification<Game> findByGenre(final Genre genre) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            final Join<Game, Genre> genreJoin = root.join(Game_.genre, JoinType.LEFT);
            return criteriaBuilder.equal(genreJoin.get(Genre_.id), genre.getId());
        };
    }
}
