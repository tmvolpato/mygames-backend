package br.com.tmvolpato.mygames.data;

import br.com.tmvolpato.mygames.model.Platform;
import br.com.tmvolpato.mygames.repository.platform.PlatformRepository;
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
 * Unit test of PlatformRepository.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @sice 2018
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class PlatformRepositoryTest {

    @Autowired
    private PlatformRepository repository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createShouldPersistData() {
        final Platform platform = new Platform("PS4");
        this.repository.save(platform);
        Assertions.assertThat(platform.getId()).isNotNull();
        Assertions.assertThat(platform.getName()).isEqualTo("PS4");
    }

    @Test
    public void deleteShouldRemoveData() {
        final Platform platform = new Platform("XBOX One");
        this.repository.save(platform);
        this.repository.delete(platform);
        Assertions.assertThat(this.repository.findById(platform.getId()).isPresent()).isEqualTo(false);
    }

    @Test
    public void updateShouldChangePersistData() {
        final Platform platform = new Platform("Nitendo");
        this.repository.save(platform);

        platform.setName("PS4 Pro");
        this.repository.save(platform);
        Assertions.assertThat(platform.getName()).isEqualTo("PS4 Pro");
    }

    @Test
    public void createWhenNameIsEmptyShouldThrownConstraintViolationException() {
        this.thrown.expect(ConstraintViolationException.class);
        final Platform platform = new Platform("");
        this.repository.save(platform);
    }

    @Test
    public void createWhenNameIsNullShouldThrownConstraintViolationException() {
        this.thrown.expect(ConstraintViolationException.class);
        final Platform platform = new Platform(null);
        this.repository.save(platform);
    }

    @Test
    public void createWhenNameUniqueShouldThrownDataIntegrityViolationException() {
        this.thrown.expect(DataIntegrityViolationException.class);

        final Platform platformOne = new Platform("PS4");
        this.repository.save(platformOne);

        final Platform platformTwo = new Platform("PS4");
        this.repository.save(platformTwo);
    }

}
