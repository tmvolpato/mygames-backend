package br.com.tmvolpato.mygames.model;

import br.com.tmvolpato.mygames.common.constant.ConstantColumn;
import br.com.tmvolpato.mygames.common.constant.ConstraintMessageValidation;
import br.com.tmvolpato.mygames.common.constant.ConstantNumeric;
import br.com.tmvolpato.mygames.common.constant.ConstantTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

/**
 * Role class.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @sice 2017
 */
@Entity
@Table(name = ConstantTable.ROLE, uniqueConstraints =
@UniqueConstraint(name = "name_uk", columnNames = { "name" }), indexes =
@Index(name = "idx_name", columnList = "name", unique = true))
@ToString(of = { "id" })
@EqualsAndHashCode(of = { "id" }, callSuper = false)
public class Role extends AbstractPersistable {

    /**
     * Serial version.
     */
    private static final long serialVersionUID = 5008991567985591980L;

    @Getter
    @Setter
    @NotBlank(message = ConstraintMessageValidation.NAME_NOT_BLANK)
    @Column(name = ConstantColumn.NAME, nullable = false, unique = true, length = ConstantNumeric.TWO_HUNDRED)
    @ApiModelProperty(notes = "The name of role", required = true)
    private String name;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name=ConstantTable.ROLE_HAS_PRIVILEGE,
            joinColumns = @JoinColumn(name = ConstantColumn.ROLE_ID, referencedColumnName = ConstantColumn.ID),
            inverseJoinColumns = @JoinColumn(name = ConstantColumn.PRIVILEGE_ID, referencedColumnName = ConstantColumn.ID))
    private Set<Privilege> privileges;

    public Role() {}

    public Role(final String name, final Set<Privilege> privileges) {
        this.name = name;
        this.addPrivileges(privileges);
    }

    private void addPrivileges(final Set<Privilege> privileges) {
         if (this.privileges == null) {
             this.privileges = new HashSet<>();
         }

         if (privileges != null || !privileges.isEmpty()) {
             this.privileges.addAll(privileges);
         }
    }
}
