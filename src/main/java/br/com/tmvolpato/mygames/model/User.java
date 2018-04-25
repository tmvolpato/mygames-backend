package br.com.tmvolpato.mygames.model;

import br.com.tmvolpato.mygames.common.constant.*;
import br.com.tmvolpato.mygames.common.util.GeneratorPassword;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * User class.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @sice 2017
 */
@Entity
@Table(name = ConstantTable.USERS, uniqueConstraints =
@UniqueConstraint(name = "email_uk", columnNames = { "email" }), indexes =
@Index(name = "idx_email", columnList = "email", unique = true))
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class User extends AbstractPersistable {

    /**
     * Serial version.
     */
    private static final long serialVersionUID = -562729759769669599L;

    @Getter
    @Setter
    @NotBlank(message = ConstraintMessageValidation.NAME_NOT_BLANK)
    @Column(name = ConstantColumn.NAME, nullable = false, length = ConstantNumeric.ONE_HUNDRED)
    private String name;

    @Getter
    @Setter
    @NotBlank(message = ConstraintMessageValidation.EMAIL_NOT_BLANK)
    @Email(message = ConstraintMessageValidation.EMAIL_VALIDATE, regexp = ConstantRegexp.EMAIL_PATTERN)
    @Column(name = ConstantColumn.EMAIL, nullable = false, unique = true, length = ConstantNumeric.ONE_HUNDRED)
    private String email;

    @Getter
    @Setter
    @NotBlank(message = ConstraintMessageValidation.PASSWORD_NOT_BLANK)
    @Column(name = ConstantColumn.PASSWORD, nullable = false)
    private String password;

    @Getter
    @Setter
    @Column(name = ConstantColumn.ENABLED)
    private boolean enabled;

    @Getter
    @Setter
    @NotEmpty(message = ConstraintMessageValidation.ROLE_NOT_NULL_OR_EMPTY)
    @NotNull(message = ConstraintMessageValidation.ROLE_NOT_NULL_OR_EMPTY)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = ConstantTable.USER_HAS_ROLE,
            joinColumns = @JoinColumn(name = ConstantColumn.USER_ID, referencedColumnName = ConstantColumn.ID),
            inverseJoinColumns = @JoinColumn(name = ConstantColumn.ROLE_ID, referencedColumnName = ConstantColumn.ID))
    private Set<Role> roles;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Game> games;

    public User() {}

    public User(final String name, final String email, final String password, final Set<Role> roles) {
        this.name = name;
        this.email = email;
        this.enabled = true;
        this.password = this.encodePassword(password);
        this.addRoles(roles);
    }

    /**
     * Codifica a senha.
     * @param password
     * @return
     */
    private String encodePassword(final String password) {
        return GeneratorPassword.encode(password);
    }

    /**
     * Adiciona um único papel para o usuário criado.
     * @param roles
     */
    private void addRoles(final Set<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return;
        }

        if (this.roles == null) {
            this.roles = new HashSet<>();
        }

        this.roles.addAll(roles);
    }
}



