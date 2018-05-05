package br.com.tmvolpato.mygames.model;

import br.com.tmvolpato.mygames.common.constant.ConstantColumn;
import br.com.tmvolpato.mygames.common.constant.ConstraintMessageValidation;
import br.com.tmvolpato.mygames.common.constant.ConstantNumeric;
import br.com.tmvolpato.mygames.common.constant.ConstantTable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Game class.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @sice 2017
 */
@Entity
@Table(name = ConstantTable.GAME)
@ToString(of = { "id" })
@EqualsAndHashCode(of = { "id" }, callSuper = false)
public class Game extends AbstractPersistable {

    /**
     * Serial Version.
     */
    private static final long serialVersionUID = 1326676340346125416L;

    /**
     * FK.
     */
    private transient final String FK = "fk_game_";

    @Getter
    @Setter
    @NotBlank(message = ConstraintMessageValidation.TITLE_NOT_BLANK)
    @Column(name = ConstantColumn.TITLE, unique = true, length = ConstantNumeric.TWO_HUNDRED)
    @ApiModelProperty(notes = "The title of game", required = true)
    private String title;

    @Getter
    @Setter
    @NotNull(message = ConstraintMessageValidation.PRICE_NOT_NULL)
    @Column(name = ConstantColumn.PRICE, nullable = false, precision = ConstantNumeric.TEN, scale = ConstantNumeric.TWO)
    @ApiModelProperty(notes = "The price of game", required = true)
    private BigDecimal price;

    @Getter
    @Setter
    @NotNull(message = ConstraintMessageValidation.PLATFORM_NOT_NULL)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = ConstantColumn.PLATFORM_ID, nullable = false, referencedColumnName = ConstantColumn.ID,
            foreignKey = @ForeignKey(name = FK + ConstantColumn.PLATFORM))
    @ApiModelProperty(notes = "Platform of game", required = true)
    private Platform platform;

    @Getter
    @Setter
    @NotNull(message = ConstraintMessageValidation.COMPANY_NOT_NULL)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = ConstantColumn.COMPANY_ID, nullable = false, referencedColumnName = ConstantColumn.ID,
            foreignKey = @ForeignKey(name = FK + ConstantColumn.COMPANY))
    @ApiModelProperty(notes = "Company of game", required = true)
    private Company company;

    @Getter
    @Setter
    @NotNull(message = ConstraintMessageValidation.GENRE_NOT_NULL)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = ConstantColumn.GENRE_ID, nullable = false, referencedColumnName = ConstantColumn.ID,
            foreignKey = @ForeignKey(name = FK + ConstantColumn.GENRE))
    @ApiModelProperty(notes = "Genre of game", required = true)
    private Genre genre;

    @JsonIgnore
    @Getter
    @Setter
    @NotNull(message = ConstraintMessageValidation.USER_NOT_NULL)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = ConstantColumn.USER_ID,  nullable = false, referencedColumnName = ConstantColumn.ID,
            foreignKey = @ForeignKey(name = FK + ConstantColumn.USER))
    @ApiModelProperty(notes = "User of system", required = true)
    private User user;

    public Game() {}

    public Game(final String title, final BigDecimal price, final Platform platform, final Company company,
                     final Genre genre, final User user) {
        this.title = title;
        this.price = price;
        this.platform = platform;
        this.company = company;
        this.genre = genre;
        this.user = user;
    }
}
