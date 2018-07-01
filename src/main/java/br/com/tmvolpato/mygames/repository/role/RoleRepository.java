package br.com.tmvolpato.mygames.repository.role;

import br.com.tmvolpato.mygames.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Interface repository of role.
 *
 * @author Thiago Michel Volpato
 * @sice 2017
 * @version 1.0.0
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

    Role findByName(String name);
}
