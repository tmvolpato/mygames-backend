package br.com.tmvolpato.mygames.repository.game.filter;

import br.com.tmvolpato.mygames.model.Company;
import br.com.tmvolpato.mygames.model.Genre;
import br.com.tmvolpato.mygames.model.Platform;
import lombok.Getter;

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
    private String title;

    @Getter
    private Platform platform;

    @Getter
    private Company company;

    @Getter
    private Genre genre;

    @Getter
    private BigDecimal price;
}
