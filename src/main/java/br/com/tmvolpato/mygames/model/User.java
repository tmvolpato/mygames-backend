package br.com.tmvolpato.mygames.model;

import br.com.tmvolpato.mygames.common.constant.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Classe usuário.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @sice 2017
 */
@Entity
@Table(name = ConstantTable.USERS)
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class User extends AbstractPersistable {

    /**
     * Serial version.
     */
    private static final long serialVersionUID = -562729759769669599L;

    /**
     * Encode ID.
     */
    private static final String ENCODE_ID = "bcrypt";

    @Getter
    @Setter
    @NotBlank(message = ConstantMessageValidation.NAME_NOT_BLANK)
    @Column(name = ConstantColumn.NAME, nullable = false, length = ConstantNumeric.ONE_HUNDRED)
    private String name;

    @Getter
    @Setter
    @NotBlank(message = ConstantMessageValidation.EMAIL_NOT_BLANK)
    @Email(message = ConstantMessageValidation.EMAIL_VALIDATE, regexp = ConstantRegexp.EMAIL_PATTERN)
    @Column(name = ConstantColumn.EMAIL, nullable = false, length = ConstantNumeric.ONE_HUNDRED)
    private String email;

    @Getter
    @Setter
    @NotBlank(message = ConstantMessageValidation.PASSWORD_NOT_BLANK)
    @Column(name = ConstantColumn.PASSWORD, nullable = false)
    private String password;

    @Getter
    @Setter
    @Column(name = ConstantColumn.ENABLED)
    private boolean enabled;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = ConstantTable.USER_HAS_ROLE,
            joinColumns = @JoinColumn(name = ConstantColumn.USER_ID, referencedColumnName = ConstantColumn.ID),
            inverseJoinColumns = @JoinColumn(name = ConstantColumn.ROLE_ID, referencedColumnName = ConstantColumn.ID))
    private Set<Role> roles;

    @Getter
    @Setter
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = ConstantTable.GAME_HAS_USER,
            joinColumns = @JoinColumn(name = ConstantColumn.GAME_ID, referencedColumnName = ConstantColumn.ID),
            inverseJoinColumns = @JoinColumn(name = ConstantColumn.USER_ID, referencedColumnName = ConstantColumn.ID))
    private Set<Game> games;

    public User() {}

    public User(final User user, final Role role) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.enabled = true;
        this.password = this.encodePassword(user.getPassword());
        this.addRole(role);
    }

    /**
     * Codifica a senha do usuário.
     * @param password
     * @return
     */
    private String encodePassword(final String password) {
        final String idForEncode = ENCODE_ID;
        final BCryptPasswordEncoder defaultEncoder = new BCryptPasswordEncoder();
        final Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idForEncode, defaultEncoder);
        final DelegatingPasswordEncoder delegatingPasswordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);
        return delegatingPasswordEncoder.encode(password);
    }

    /**
     * Adiciona um único papel para o usuário criado.
     * @param role
     */
    private void addRole(final Role role) {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }

        if (role != null) {
            this.roles.add(role);
        }
    }
}



