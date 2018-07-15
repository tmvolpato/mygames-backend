package br.com.tmvolpato.mygames.repository.user.specification;

import br.com.tmvolpato.mygames.model.Role_;
import br.com.tmvolpato.mygames.model.User;
import br.com.tmvolpato.mygames.model.User_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;

/**
 * Specification query of user.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
public final class UserSpecification {

    /**
     * Find by id of user.
     *
     * @param id
     * @return
     */
    public static Specification<User> findById(final Long id) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            root
                    .fetch(User_.roles, JoinType.LEFT)
                    .fetch(Role_.privileges, JoinType.LEFT);
            final Expression<Long> property = root.get(User_.id);
            return criteriaBuilder.equal(property, id);
        });
    }

    /**
     * Find by email of user.
     *
     * @param email
     * @return
     */
    public static Specification<User> findByEmail(final String email) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            root
                    .fetch(User_.roles, JoinType.LEFT)
                    .fetch(Role_.privileges, JoinType.LEFT);
            final Expression<String> property = root.get(User_.email);
            return criteriaBuilder.equal(property, email);
        });
    }
}
