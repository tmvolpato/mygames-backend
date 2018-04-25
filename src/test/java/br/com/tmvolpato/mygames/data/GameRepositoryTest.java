package br.com.tmvolpato.mygames.data;

import br.com.tmvolpato.mygames.model.*;
import br.com.tmvolpato.mygames.repository.company.CompanyRepository;
import br.com.tmvolpato.mygames.repository.game.GameRepository;
import br.com.tmvolpato.mygames.repository.genre.GenreRepository;
import br.com.tmvolpato.mygames.repository.platform.PlatformRepository;
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
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Unit test of GamesRepository.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @sice 2018
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class GameRepositoryTest {

    @Autowired
    private GameRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private PlatformRepository platformRepository;

    @Autowired
    private UserRepository userRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createShouldPersistData() {
        final User owner = this.createUser();

        final Game game = new Game("Assassins Creed IV", new BigDecimal(89.00), this.createPlatform(), this.createCompany(), this.createGenre(), owner);
        this.repository.save(game);
        Assertions.assertThat(game.getId()).isNotNull();
        Assertions.assertThat(game.getTitle()).isEqualTo("Assassins Creed IV");
        Assertions.assertThat(game.getPrice()).isEqualByComparingTo(new BigDecimal(89.00));
        Assertions.assertThat(game.getPlatform().getName()).isEqualTo("Playstation 4");
        Assertions.assertThat(game.getCompany().getName()).isEqualTo("Ubisoft");
        Assertions.assertThat(game.getGenre().getName()).isEqualTo("Action");
        Assertions.assertThat(game.getUser().getName()).isEqualTo("User Test");
    }

    @Test
    public void deleteShouldRemoveData() {
        final User owner = this.createUser();

        final Game game = new Game("Assassins Creed IV", new BigDecimal(89.00), this.createPlatform(), this.createCompany(), this.createGenre(), owner);
        this.repository.save(game);
        this.repository.delete(game);
        Assertions.assertThat(this.repository.findById(game.getId()).isPresent()).isEqualTo(false);
    }

    @Test
    public void updateShouldChangePersistData() {
        final User owner = this.createUser();

        final Game game = new Game("For Honor", new BigDecimal(79.00), this.createPlatform(), this.createCompany(), this.createGenre(), owner);
        this.repository.save(game);
        game.setTitle("Assassins Creed IV");
        game.setPrice(new BigDecimal(89.00));
        this.repository.save(game);
        Assertions.assertThat(game.getTitle()).isEqualTo("Assassins Creed IV");
        Assertions.assertThat(game.getPrice()).isEqualByComparingTo(new BigDecimal(89.00));
    }

    @Test
    public void countShouldQuantityData() {
        final User owner = this.createUser();

        final Platform platform = createPlatform();
        final Company company = createCompany();
        final Genre genre = createGenre();

        final Game gameOne = new Game("Assassins Creed IV", new BigDecimal(89.00), platform, company, genre, owner);
        this.repository.save(gameOne);

        final Game gameTwo = new Game("Assasssins Creed Origins", new BigDecimal(149.00), platform, company, genre, owner);
        this.repository.save(gameTwo);

        final Game gameThree = new Game("For Honor", new BigDecimal(79.00), platform, company, genre, owner);
        this.repository.save(gameThree);

        Assertions.assertThat(this.repository.count(owner, null)).isEqualTo(3L);
    }

    @Test
    public void createWhenTitleIsNullShouldThrownConstraintValidationException() {
        this.thrown.expect(ConstraintViolationException.class);

        final User owner = this.createUser();

        final Game game = new Game(null, new BigDecimal(89.00), this.createPlatform(), this.createCompany(), this.createGenre(), owner);
        this.repository.save(game);
    }

    @Test
    public void createWhenTitleIsEmptyShouldThrownConstraintValidationException() {
        this.thrown.expect(ConstraintViolationException.class);

        final User owner = this.createUser();

        final Game game = new Game("", new BigDecimal(89.00), this.createPlatform(), this.createCompany(), this.createGenre(), owner);
        this.repository.save(game);
    }


    @Test
    public void createWhenPlatformIsNullShouldThrownConstraintValidationException() {
        this.thrown.expect(ConstraintViolationException.class);

        final User owner = this.createUser();

        final Game game = new Game("Assasssins Creed Origins", new BigDecimal(149.00), null, this.createCompany(), this.createGenre(), owner);
        this.repository.save(game);
    }

    @Test
    public void createWhenCompanyIsNullShouldThrownConstraintValidationException() {
        this.thrown.expect(ConstraintViolationException.class);

        final User owner = this.createUser();

        final Game game = new Game("For Honor", new BigDecimal(79.00), this.createPlatform(), null, this.createGenre(), owner);
        this.repository.save(game);
    }

    @Test
    public void createWhenGenreIsNullShouldThrownConstraintValidationException() {
        this.thrown.expect(ConstraintViolationException.class);

        final User owner = this.createUser();

        final Game game = new Game("Far Cry 5", new BigDecimal(200.00), this.createPlatform(), this.createCompany(), null, owner);
        this.repository.save(game);
    }

    @Test
    public void createWhenOwnerIsNullShouldTrownConstraintValidationException() {
        this.thrown.expect(ConstraintViolationException.class);

        final Game game = new Game("Assassins Creed IV", new BigDecimal(89.00), this.createPlatform(), this.createCompany(), this.createGenre(), null);
        this.repository.save(game);
    }


    private User createUser() {
        final User user = new User("User Test", "user_test@email.com", "user@test", this.createRoles());
        return this.userRepository.save(user);
    }

    private Set<Role> createRoles() {
        Set<Role> roles = new HashSet<>();
        final Role role = new Role("ROLE_TEST", this.createPrivileges());
        this.roleRepository.save(role);
        roles.add(role);
        return roles;
    }

    private Set<Privilege> createPrivileges() {
        Set<Privilege> privileges = new HashSet<>();
        final Privilege privilegeRead = new Privilege("READ_PRIVILEGE_TEST");
        privileges.add(this.privilegeRepository.save(privilegeRead));

        final Privilege privilegeWrite = new Privilege("WRITE_PRIVILEGE_TEST");
        privileges.add(this.privilegeRepository.save(privilegeWrite));
        return privileges;
    }

    private Company createCompany() {
        final Company company = new Company("Ubisoft");
        return this.companyRepository.save(company);
    }

    private Platform createPlatform() {
        final Platform platform = new Platform("Playstation 4");
        return this.platformRepository.save(platform);
    }

    private Genre createGenre() {
        final Genre genre = new Genre("Action");
        return this.genreRepository.save(genre);
    }

}
