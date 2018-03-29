package br.com.tmvolpato.mygames.service.User;

import br.com.tmvolpato.mygames.model.User;
import br.com.tmvolpato.mygames.repository.user.UserRepository;
import br.com.tmvolpato.mygames.repository.user.specification.UserSpecification;
import br.com.tmvolpato.mygames.service.ServicePreconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implementação do serviço do usuário.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @since 2017
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(final User user) {
        ServicePreconditions.checkEntityExists(user);
        return this.userRepository.save(user);
    }

    @Override
    public User update(final User user) {
        ServicePreconditions.checkEntityExists(user);
        return this.userRepository.saveAndFlush(user);
    }

    @Override
    public void delete(final Long primaryKey) {
        final Optional<User> user = this.findById(primaryKey);
        ServicePreconditions.checkEntityExists(user);
        this.userRepository.delete(user.get());
    }

    @Override
    public Optional<User> findById(final Long id) {
        ServicePreconditions.checkParameterLong(id);
        return this.userRepository.findOne(Specification.where(UserSpecification.findById(id)));
    }

    @Override
    public Optional<User> findUsername(final String email) {
        ServicePreconditions.checkParameterString(email);
        return this.userRepository.findOne(Specification.where(UserSpecification.findByEmail(email)));
    }
}
