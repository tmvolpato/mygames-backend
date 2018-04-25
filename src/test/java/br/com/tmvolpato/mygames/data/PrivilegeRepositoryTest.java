package br.com.tmvolpato.mygames.data;

import br.com.tmvolpato.mygames.model.Privilege;
import br.com.tmvolpato.mygames.repository.privilege.PrivilegeRepository;
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
 * Unit test of PrivilegeRepository.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @sice 2018
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class PrivilegeRepositoryTest {

    @Autowired
    private PrivilegeRepository repository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createShouldPersistData() {
        final Privilege privilege = new Privilege("READ_PRIVILEGE");
        this.repository.save(privilege);
        Assertions.assertThat(privilege.getId()).isNotNull();
        Assertions.assertThat(privilege.getName()).isEqualTo("READ_PRIVILEGE");
    }

    @Test
    public void deleteShouldRemoveData() {
        final Privilege privilege = new Privilege("READ_PRIVILEGE");
        this.repository.save(privilege);

        this.repository.delete(privilege);
        Assertions.assertThat(this.repository.findById(privilege.getId()).isPresent()).isEqualTo(false);
    }

    @Test
    public void updateChangePersistData() {
        final Privilege privilege = new Privilege("WRITE_PRIVILEGE");
        this.repository.save(privilege);

        privilege.setName("READ_PRIVILEGE");
        this.repository.save(privilege);
        Assertions.assertThat(privilege.getName()).isEqualTo("READ_PRIVILEGE");
    }

    @Test
    public void createWhenNameIsEmptyShouldThrownConstraintViolationException() {
        this.thrown.expect(ConstraintViolationException.class);
        final Privilege privilege = new Privilege("");
        this.repository.save(privilege);
    }

    @Test
    public void createWhenNameIsNullShouldThrownConstraintViolationException() {
        this.thrown.expect(ConstraintViolationException.class);
        final Privilege privilege = new Privilege(null);
        this.repository.save(privilege);
    }

    @Test
    public void createWhenNameUniqueShouldThrownDataIntegrityViolationException() {
        this.thrown.expect(DataIntegrityViolationException.class);

        final Privilege privilegeOne = new Privilege("WRITE_PRIVILEGE");
        this.repository.save(privilegeOne);

        final Privilege privilegeTwo = new Privilege("WRITE_PRIVILEGE");
        this.repository.save(privilegeTwo);
    }
}
