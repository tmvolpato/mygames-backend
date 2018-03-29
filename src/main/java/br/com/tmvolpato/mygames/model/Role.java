package br.com.tmvolpato.mygames.model;

import br.com.tmvolpato.mygames.common.constant.ConstantColumn;
import br.com.tmvolpato.mygames.common.constant.ConstantMessageValidation;
import br.com.tmvolpato.mygames.common.constant.ConstantNumeric;
import br.com.tmvolpato.mygames.common.constant.ConstantTable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * Classe papel do usuário no sistema.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @sice 2017
 */
@Entity
@Table(name = ConstantTable.ROLE)
@ToString(of = { "id" })
@EqualsAndHashCode(of = { "id" }, callSuper = false)
public class Role extends AbstractPersistable {

    /**
     * Serial version.
     */
    private static final long serialVersionUID = 5008991567985591980L;

    @Getter
    @Setter
    @NotBlank(message = ConstantMessageValidation.NAME_NOT_BLANK)
    @Column(name = ConstantColumn.NAME, nullable = false, unique = true, length = ConstantNumeric.TWO_HUNDRED)
    private String name;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name=ConstantTable.ROLE_HAS_PRIVILEGE,
            joinColumns = @JoinColumn(name = ConstantColumn.ROLE_ID, referencedColumnName = ConstantColumn.ID),
            inverseJoinColumns = @JoinColumn(name = ConstantColumn.PRIVILEGE_ID, referencedColumnName = ConstantColumn.ID))
    private Set<Privilege> privileges;

    public Role() {}
}
