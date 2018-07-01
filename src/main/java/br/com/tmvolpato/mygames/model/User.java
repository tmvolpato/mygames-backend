package br.com.tmvolpato.mygames.model;

import br.com.tmvolpato.mygames.common.constant.*;
import br.com.tmvolpato.mygames.common.util.GeneratorPassword;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.*;

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
    @ApiModelProperty(notes = "The name of user", required = true)
    private String name;

    @Getter
    @Setter
    @NotBlank(message = ConstraintMessageValidation.EMAIL_NOT_BLANK)
    @Email(message = ConstraintMessageValidation.EMAIL_VALIDATE, regexp = ConstantRegexp.EMAIL_PATTERN)
    @Column(name = ConstantColumn.EMAIL, nullable = false, unique = true, length = ConstantNumeric.ONE_HUNDRED)
    @ApiModelProperty(notes = "E-mail of user", required = true)
    private String email;

    @JsonProperty(access = WRITE_ONLY)
    @Getter
    @Setter
    @NotBlank(message = ConstraintMessageValidation.PASSWORD_NOT_BLANK)
    @Column(name = ConstantColumn.PASSWORD, nullable = false)
    @ApiModelProperty(notes = "Password of user", required = true)
    private String password;

    @JsonIgnore
    @Getter
    @Setter
    @Column(name = ConstantColumn.ENABLED)
    @ApiModelProperty(notes = "User active yes or no", hidden = true)
    private boolean enabled;

    @JsonIgnore
    @Getter
    @Setter
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
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }

        this.roles.addAll(roles);
    }
}



