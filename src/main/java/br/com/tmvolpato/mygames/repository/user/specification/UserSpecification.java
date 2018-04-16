package br.com.tmvolpato.mygames.repository.user.specification;

import br.com.tmvolpato.mygames.model.Role_;
import br.com.tmvolpato.mygames.model.User;
import br.com.tmvolpato.mygames.model.User_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;

/**
 * Query especifica do usuário.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @sice 2017
 */
public final class UserSpecification {

    /**
     * Procura pelo ID.
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
     * Procura pelo email.
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
