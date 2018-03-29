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
 * Classe empresa.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @sice 2017
 */
@Entity
@Table(name = ConstantTable.COMPANY)
@ToString(of = { "id" })
@EqualsAndHashCode(of = { "id" }, callSuper = false)
public class Company extends AbstractPersistable {

    /**
     * Serial version.
     */
    private static final long serialVersionUID = 7737686171142129119L;

    @Getter
    @Setter
    @NotBlank(message = ConstantMessageValidation.NAME_NOT_BLANK)
    @Column(name = ConstantColumn.NAME, nullable = false, unique = true, length = ConstantNumeric.TWO_HUNDRED)
    private String name;
}
