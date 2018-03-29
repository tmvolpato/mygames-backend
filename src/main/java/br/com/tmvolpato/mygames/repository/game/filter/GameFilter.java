package br.com.tmvolpato.mygames.repository.game.filter;

import br.com.tmvolpato.mygames.model.Company;
import br.com.tmvolpato.mygames.model.Genre;
import br.com.tmvolpato.mygames.model.Platform;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Classe filtro do game.
 *
 * @author Thiago Michel Volpato
 * @sice 2017
 * @version 1.0.0
 */
public class GameFilter {

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private Platform platform;

    @Getter
    @Setter
    private Company company;

    @Getter
    @Setter
    private Genre genre;

    @Getter
    @Setter
    private BigDecimal price;
}
