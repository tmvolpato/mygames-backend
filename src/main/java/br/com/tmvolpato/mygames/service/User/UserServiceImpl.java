package br.com.tmvolpato.mygames.service.User;

import br.com.tmvolpato.mygames.common.web.exception.MyUnauthorizedException;
import br.com.tmvolpato.mygames.model.Role;
import br.com.tmvolpato.mygames.model.User;
import br.com.tmvolpato.mygames.repository.role.RoleRepository;
import br.com.tmvolpato.mygames.repository.user.UserRepository;
import br.com.tmvolpato.mygames.repository.user.specification.UserSpecification;
import br.com.tmvolpato.mygames.service.ServicePreconditions;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implementation user service.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @since 2017
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository, final RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User create(final User userLogged, final User user) {
        ServicePreconditions.checkEntityExists(user);
        final Role roleUser = this.roleRepository.findByName("USER");
        final Optional<User> userExist = this.userRepository.findOne(Specification.where(UserSpecification.findByEmail(user.getEmail())));
        ServicePreconditions.checkIfAlreadyExist(userExist.isPresent());
        final User newUser = new User(user.getName(), user.getEmail(), user.getPassword(), Sets.newHashSet(roleUser));
        this.checkRole(newUser);
        return this.userRepository.save(newUser);
    }

    @Override
    public User update(final User userLogged, final User user) {
        ServicePreconditions.checkEntityExists(userLogged);
        ServicePreconditions.checkEntityExists(user);
        ServicePreconditions.checkResourceOwner(userLogged.getId(), user.getId());
        this.checkRole(user);
        return this.userRepository.saveAndFlush(user);
    }

    @Override
    public void delete(final User userLogged, final Long id) {
        final Optional<User> user = this.findById(userLogged, id);
        this.userRepository.delete(user.get());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(final User userLogged, final Long id) {
        ServicePreconditions.checkEntityExists(userLogged);
        ServicePreconditions.checkParameterLong(id);
        ServicePreconditions.checkResourceOwner(userLogged.getId(), id);
        final Optional<User> user = this.userRepository.findOne(Specification.where(UserSpecification.findById(id)));
        ServicePreconditions.checkEntityExists(user.isPresent());
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUsername(final String email) {
        ServicePreconditions.checkParameterString(email);
        final Optional<User> user = this.userRepository.findOne(Specification.where(UserSpecification.findByEmail(email)));
        ServicePreconditions.checkEntityExists(user.isPresent());
        return user;
    }

    /**
     * Check if role is null or empty.
     * @param user
     */
    private void checkRole(final User user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            throw new MyUnauthorizedException();
        }
    }
}
