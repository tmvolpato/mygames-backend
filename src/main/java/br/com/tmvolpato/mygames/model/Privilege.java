package br.com.tmvolpato.mygames.model;

import br.com.tmvolpato.mygames.common.constant.ConstantColumn;
import br.com.tmvolpato.mygames.common.constant.ConstantMessageValidation;
import br.com.tmvolpato.mygames.common.constant.ConstantNumeric;
import br.com.tmvolpato.mygames.common.constant.ConstantTable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * Classe privilegio.
 *
 * @author Thiago Michel Volpato
 * @sice 2017
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
    @NotBlank(message = ConstantMessageValidation.NAME_NOT_BLANK)
    @Column(name = ConstantColumn.NAME, nullable = false, length = ConstantNumeric.ONE_HUNDRED)
    private String name;

    public Privilege() {}

    public Privilege(final String name) {
        this.name = name;
    }
}
