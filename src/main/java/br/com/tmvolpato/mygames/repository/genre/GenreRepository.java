package br.com.tmvolpato.mygames.repository.genre;

import br.com.tmvolpato.mygames.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Implementation repository of genre.
 *
 * @author Thiago Michel Volpato
 * @sice 2018
 * @version 1.0.0
 */
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long>{
}
