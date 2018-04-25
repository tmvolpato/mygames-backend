package br.com.tmvolpato.mygames.data;

import br.com.tmvolpato.mygames.model.Privilege;
import br.com.tmvolpato.mygames.model.Role;
import br.com.tmvolpato.mygames.model.User;
import br.com.tmvolpato.mygames.repository.privilege.PrivilegeRepository;
import br.com.tmvolpato.mygames.repository.role.RoleRepository;
import br.com.tmvolpato.mygames.repository.user.UserRepository;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Unit test of UserRepository.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @sice 2018
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createShouldPersistData() {
      final User user = this.createUser();
      Assertions.assertThat(user.getId()).isNotNull();
      Assertions.assertThat(user.getName()).isEqualTo("User Test");
      Assertions.assertThat(user.getEmail()).isEqualTo("user@email.com");
      Assertions.assertThat(user.getRoles()).isNotEmpty();
      Assertions.assertThat(user.getRoles().stream().map(r -> r.getPrivileges())).isNotEmpty();
    }

    @Test
    public void updateChangePersistData() {
        final User user = this.createUser();
        user.setName("User Change");
        Assertions.assertThat(user.getId()).isNotNull();
        Assertions.assertThat(user.getName()).isEqualTo("User Change");
        Assertions.assertThat(user.getEmail()).isEqualTo("user@email.com");
        Assertions.assertThat(user.getRoles()).isNotEmpty();
        Assertions.assertThat(user.getRoles().stream().map(r -> r.getPrivileges())).isNotEmpty();
    }

    @Test
    public void deleteShouldRemoveData() {
        final User user = this.createUser();
        this.repository.delete(user);
        Assertions.assertThat(this.repository.findById(user.getId()).isPresent()).isEqualTo(false);
    }

    @Test
    public void createWhenNameIsNullShouldThrownConstraintViolationException() {
        this.thrown.expect(ConstraintViolationException.class);

        final User user = new User(null, "user@email.com", "admin@test", this.createRoles());
        this.repository.save(user);
    }

    @Test
    public void createWhenNameIsEmptyShouldThrownConstraintViolationException() {
        this.thrown.expect(ConstraintViolationException.class);

        final User user = new User("", "user@email.com", "admin@test", this.createRoles());
        this.repository.save(user);
    }

    @Test
    public void createWhenEmailIsNullShouldThrownConstraintViolationException() {
        this.thrown.expect(ConstraintViolationException.class);

        final User user = new User("User Test", null, "admin@test", this.createRoles());
        this.repository.save(user);
    }

    @Test
    public void createWhenEmailIsEmptyShouldThrownConstraintViolationException() {
        this.thrown.expect(ConstraintViolationException.class);

        final User user = new User("User Test", "", "admin@test", this.createRoles());
        this.repository.save(user);
    }

    @Test
    public void createWhenEmailInvalidShouldThrownConstraintViolationException() {
        this.thrown.expect(ConstraintViolationException.class);

        final User user = new User("User Test", "user@email", "admin@test", this.createRoles());
        this.repository.save(user);
    }

    @Test
    public void createWhenEmailUniqueShouldThrownDataIntegrityViolationException() {
        this.thrown.expect(DataIntegrityViolationException.class);

        final User userOne = new User("User Test", "user@email.com", "admin@test", this.createRoles());
        this.repository.save(userOne);

        final User userTwo = new User("User Test", "user@email.com", "admin@test", this.createRoles());
        this.repository.save(userTwo);
    }

    @Test
    public void createWhenRolesIsNullShouldThrownConstraintViolationException() {
        this.thrown.expect(ConstraintViolationException.class);

        final User user = new User("User Test", "user@email.com", "admin@test", null);
        this.repository.save(user);
    }

    @Test
    public void createWhenRolesIsEmptyShouldThrownConstraintViolationException() {
        this.thrown.expect(ConstraintViolationException.class);

        final User user = new User("User Test", "user@email.com", "admin@test", Collections.emptySet());
        this.repository.save(user);
    }

    private User createUser() {
        final User user = new User("User Test", "user@email.com", "admin@test", this.createRoles());
        return this.repository.save(user);
    }

    private Set<Role> createRoles() {
        Set<Role> roles = new HashSet<>();
        final Role role = new Role("ROLE_USER", this.createPrivileges());
        this.roleRepository.save(role);
        roles.add(role);
        return roles;
    }

    private Set<Privilege> createPrivileges() {
        Set<Privilege> privileges = new HashSet<>();
        final Privilege privilegeRead = new Privilege("READ_PRIVILEGE");
        privileges.add(this.privilegeRepository.save(privilegeRead));

        final Privilege privilegeWrite = new Privilege("WRITE_PRIVILEGE");
        privileges.add(this.privilegeRepository.save(privilegeWrite));
        return privileges;
    }
}
