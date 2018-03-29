package br.com.tmvolpato.mygames.model;

import br.com.tmvolpato.mygames.common.constant.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
@ToString(of = { "id" })
@EqualsAndHashCode(of = { "id" }, callSuper = false)
public class User extends AbstractPersistable {

    /**
     * Serial version.
     */
    private static final long serialVersionUID = -562729759769669599L;

    /**
     * FK.
     */
    private transient final String FK = "fk_user_";

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
    @JsonIgnore
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
}
