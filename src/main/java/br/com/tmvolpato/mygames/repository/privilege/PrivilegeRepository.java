package br.com.tmvolpato.mygames.repository.privilege;

import br.com.tmvolpato.mygames.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface repository of privilege.
 *
 * @author Thiago Michel Volpato
 * @sice 2018
 * @version 1.0.0
 */
@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
}
