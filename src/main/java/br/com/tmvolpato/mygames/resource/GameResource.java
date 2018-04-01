package br.com.tmvolpato.mygames.resource;

import br.com.tmvolpato.mygames.common.constant.ConstantNumeric;
import br.com.tmvolpato.mygames.common.constant.ConstantQuery;
import br.com.tmvolpato.mygames.common.web.util.Mappings;
import br.com.tmvolpato.mygames.model.Game;
import br.com.tmvolpato.mygames.model.User;
import br.com.tmvolpato.mygames.repository.game.filter.GameFilter;
import br.com.tmvolpato.mygames.service.User.UserService;
import br.com.tmvolpato.mygames.service.game.GameService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Classe de recurso do game.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @since 2017
 */
@RestController
@RequestMapping(value = Mappings.GAMES)
public class GameResource extends AbstractResource<Game> {

    private final GameService gameService;

    private final UserService userService;

    @Autowired
    public GameResource(final GameService gameService, final UserService userService) {
        super(Game.class);

        this.gameService = gameService;
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER') and #oauth2.hasScope('write')")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody final Game game, final UriComponentsBuilder uriBuilder,
                       final HttpServletResponse response) {
        this.checkRequiredCreateInternal(game);
        final Game gameSaved = this.gameService.create(game);
        this.createPublishEvent(gameSaved, uriBuilder, response);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER') and #oauth2.hasScope('write')")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") final Long id, @Valid @RequestBody final Game game) {
        this.checkRequiredUpdateInternal(id, game);
        this.gameService.update(game);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER') and #oauth2.hasScope('write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") final Long id) {
        this.checkRequiredPrimaryKeyDeleteInternal(id);
        final Optional<Game> game = this.gameService.findById(this.getUserLogged(), id);
        this.gameService.delete(game.get());
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER') and #oauth2.hasScope('read')")
    public ResponseEntity<Game> findByGameId(@PathVariable("id") final Long id, final UriComponentsBuilder uriBuilder,
                                             final HttpServletResponse response) {

        final Optional<Game> gameFound = this.gameService.findById(this.getUserLogged(), id);
        this.checkRequiredSingleResourceInternal(gameFound.get());
        this.singlePublishEvent(uriBuilder, response);
        return ResponseEntity.status(HttpStatus.OK)
                                    .cacheControl(CacheControl.maxAge(ConstantNumeric.ONE_HUNDRED, TimeUnit.SECONDS))
                                    .body(gameFound.get());
    }

    @GetMapping(params = { ConstantQuery.PAGE, ConstantQuery.SIZE })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER') and #oauth2.hasScope('read')")
    public ResponseEntity<List<Game>> findAllPaginatedAndFilter(final GameFilter gameFilter,
                                                                @RequestParam(value = ConstantQuery.PAGE) final int page,
                                                                @RequestParam(value = ConstantQuery.SIZE) final int size,
                                                                final UriComponentsBuilder uriBuilder,
                                                                final HttpServletResponse response) {

    final Page<Game> resultPages = this.gameService.findAllPaginatedAndFilter(this.getUserLogged(), gameFilter, page, size);
    this.checkRequiredPaginatedResourceInternal(page, resultPages);
    this.paginatedPublishEvent(page, size, resultPages.getTotalPages(), uriBuilder, response);
    return ResponseEntity.status(HttpStatus.OK)
            .cacheControl(CacheControl.maxAge(ConstantNumeric.ONE_HUNDRED, TimeUnit.SECONDS))
            .body(Lists.newArrayList(resultPages.getContent()));
    }

    private User getUserLogged() {
        return this.userService.findUsername(super.getPrincipal()).get();
    }

}
