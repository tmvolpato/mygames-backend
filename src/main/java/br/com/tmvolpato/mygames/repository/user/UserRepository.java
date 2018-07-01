package br.com.tmvolpato.mygames.repository.user;

import br.com.tmvolpato.mygames.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Interface repository of user.
 *
 * @author Thiago Michel Volpato
 * @sice 2017
 * @version 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
}
