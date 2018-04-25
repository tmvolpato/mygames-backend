package br.com.tmvolpato.mygames.model;

import br.com.tmvolpato.mygames.common.constant.ConstantColumn;
import br.com.tmvolpato.mygames.common.constant.ConstraintMessageValidation;
import br.com.tmvolpato.mygames.common.constant.ConstantNumeric;
import br.com.tmvolpato.mygames.common.constant.ConstantTable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * Platform class.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @sice 2017
 */
@Entity
@Table(name = ConstantTable.PLATFORM, uniqueConstraints =
@UniqueConstraint(name = "name_uk", columnNames = { "name" }), indexes =
@Index(name = "idx_name", columnList = "name", unique = true))
@ToString(of = { "id" })
@EqualsAndHashCode(of = { "id" }, callSuper = false)
public class Platform extends AbstractPersistable {


    /**
     * Serial version.
     */
    private static final long serialVersionUID = -165875664967278076L;

    @Getter
    @Setter
    @NotBlank(message = ConstraintMessageValidation.NAME_NOT_BLANK)
    @Column(name = ConstantColumn.NAME, nullable = false, unique = true, length = ConstantNumeric.TWO_HUNDRED)
    private String name;

    public Platform() {}

    public Platform(final String name) {
        this.name = name;
    }
}
