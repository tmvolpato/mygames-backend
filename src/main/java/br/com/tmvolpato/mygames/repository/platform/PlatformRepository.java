package br.com.tmvolpato.mygames.repository.platform;

import br.com.tmvolpato.mygames.model.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository of class Platform.
 *
 * @author Thiago Michel Volpato
 * @sice 2018
 * @version 1.0.0
 */
@Repository
public interface PlatformRepository extends JpaRepository<Platform, Long> {
}
