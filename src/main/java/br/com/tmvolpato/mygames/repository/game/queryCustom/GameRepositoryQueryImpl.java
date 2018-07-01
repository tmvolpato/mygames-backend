package br.com.tmvolpato.mygames.repository.game.queryCustom;

import br.com.tmvolpato.mygames.model.*;
import br.com.tmvolpato.mygames.repository.AbstractQuery;
import br.com.tmvolpato.mygames.repository.game.filter.GameFilter;
import br.com.tmvolpato.mygames.repository.game.specification.GameSpecification;
import br.com.tmvolpato.mygames.service.security.UserApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation custom query of game.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @sice 2017
 */
public class GameRepositoryQueryImpl extends AbstractQuery implements GameRepositoryQuery {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Game> findAllPaginatedAndFilter(final UserApplication userApplication, final GameFilter gameFilter, final int page, final int size) {
        final CriteriaBuilder builder = this.em.getCriteriaBuilder();
        final CriteriaQuery<Game> criteria = builder.createQuery(Game.class);
        final Root<Game> root = criteria.from(Game.class);

        root.fetch(Game_.company, JoinType.LEFT);
        root.fetch(Game_.platform, JoinType.LEFT);
        root.fetch(Game_.genre, JoinType.LEFT);
        root.fetch(Game_.user, JoinType.LEFT)
                .fetch(User_.roles, JoinType.LEFT)
                .fetch(Role_.privileges, JoinType.LEFT);

        final Predicate[] predicates = this.createPredicates(userApplication, gameFilter, criteria, builder, root);
        criteria.where(predicates);
        criteria.orderBy(builder.asc(root.get(Game_.title)));
        criteria.select(root);
        final TypedQuery<Game> query = this.em.createQuery(criteria);
        final Pageable pageable = PageRequest.of(page, size);
        this.addRestrictionPagined(query, pageable);
        return new PageImpl<>(query.getResultList(), pageable, this.count(userApplication, gameFilter));
    }

    @Override
    public long count(final UserApplication userApplication, final GameFilter gameFilter) {
        final CriteriaBuilder builder = this.em.getCriteriaBuilder();
        final CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        final Root<Game> root = criteria.from(Game.class);

        final Predicate[] predicates = this.createPredicates(userApplication, gameFilter, criteria, builder, root);
        criteria.where(predicates);
        criteria.select(builder.count(root));
        return this.em.createQuery(criteria).getSingleResult().longValue();
    }


    /**
     * Filtros de pesquisa.
     *
     * @param userApplication
     * @param gameFilter
     * @param criteriaQuery
     * @param builder
     * @param root
     * @return
     */
    private Predicate[] createPredicates(final UserApplication userApplication, final GameFilter gameFilter,
                                         final CriteriaQuery<?> criteriaQuery,
                                         final CriteriaBuilder builder,
                                         final Root<Game> root) {

       final List<Predicate> predicates = new LinkedList<>();
       final Predicate predicate = builder.equal(root.get(Game_.user).get(User_.id), userApplication.getId());

        predicates.add(predicate);

        if (gameFilter != null) {

            if (!StringUtils.isEmpty(gameFilter.getTitle())) {
                final Specification specification = GameSpecification.findByTitle(gameFilter.getTitle());
                predicates.add(specification.toPredicate(root, criteriaQuery, builder));
            }

            if (gameFilter.getCompany() != null && gameFilter.getCompany().getId() != null) {
                final Specification specification = GameSpecification.findByCompany(gameFilter.getCompany());
                predicates.add(specification.toPredicate(root, criteriaQuery, builder));
            }

            if (gameFilter.getGenre() != null && gameFilter.getGenre().getId() != null) {
                final Specification specification = GameSpecification.findByGenre(gameFilter.getGenre());
                predicates.add(specification.toPredicate(root, criteriaQuery, builder));
            }

            if (gameFilter.getPlatform() != null && gameFilter.getPlatform().getId() != null) {
                final Specification specification = GameSpecification.findByPlatform(gameFilter.getPlatform());
                predicates.add(specification.toPredicate(root, criteriaQuery, builder));
            }
        }

        return predicates.toArray(new Predicate[ predicates.size() ]);

    }

}
