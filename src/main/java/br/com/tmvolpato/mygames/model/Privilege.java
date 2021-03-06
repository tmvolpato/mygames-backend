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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * Privilege class.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
@Entity
@Table(name = ConstantTable.PRIVILEGE)
@ToString(of = { "id", "name" })
@EqualsAndHashCode(of = { "id", "name" }, callSuper = false)
public class Privilege extends AbstractPersistable {

    /**
     * Serial version.
     */
    private static final long serialVersionUID = -1802960066640261801L;

    @Getter
    @Setter
    @NotBlank(message = ConstraintMessageValidation.NAME_NOT_BLANK)
    @Column(name = ConstantColumn.NAME, nullable = false, unique = true, length = ConstantNumeric.ONE_HUNDRED)
    @ApiModelProperty(notes = "The name of privilege", required = true)
    private String name;

    public Privilege() {}

    public Privilege(final String name) {
        this.name = name;
    }
}
