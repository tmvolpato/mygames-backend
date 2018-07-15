package br.com.tmvolpato.mygames.data;

import br.com.tmvolpato.mygames.model.Genre;
import br.com.tmvolpato.mygames.repository.genre.GenreRepository;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

/**
 * Unit test of GenreRepository.
 *
 * @author Thiago Michel Volpato
 * @since 2018
 * @version 1.0.0
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class GenreRepositoryTest {

    @Autowired
    private GenreRepository repository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createShouldPersistData() {
        final Genre genre = new Genre("RPG");
        this.repository.save(genre);
        Assertions.assertThat(genre.getId()).isNotNull();
        Assertions.assertThat(genre.getName()).isEqualTo("RPG");
    }

    @Test
    public void deleteShouldRemoveData() {
        final Genre genre = new Genre("Action");
        this.repository.save(genre);
        this.repository.delete(genre);
        Assertions.assertThat(this.repository.findById(genre.getId()).isPresent()).isEqualTo(false);
    }

    @Test
    public void updateShouldChangePersistData() {
        final Genre genre = new Genre("Simulation");
        this.repository.save(genre);

        genre.setName("Sport");
        this.repository.save(genre);
        Assertions.assertThat(genre.getName()).isEqualTo("Sport");
    }

    @Test
    public void createWhenNameIsEmptylShouldThrownConstraintViolationException() {
        this.thrown.expect(ConstraintViolationException.class);
        this.thrown.expectMessage("name.not.blank");
        final Genre genre = new Genre("");
        this.repository.save(genre);
    }

    @Test
    public void createWhenNameIsNulllShouldThrownConstraintViolationException() {
        this.thrown.expect(ConstraintViolationException.class);
        this.thrown.expectMessage("name.not.blank");
        final Genre genre = new Genre(null);
        this.repository.save(genre);
    }

    @Test
    public void createWhenNameUniqueShouldThrownDataIntegrityViolationException() {
        this.thrown.expect(DataIntegrityViolationException.class);

        final Genre genreOne = new Genre("Action");
        this.repository.save(genreOne);

        final Genre genreTwo = new Genre("Action");
        this.repository.save(genreTwo);
    }
}
