package br.com.tmvolpato.mygames.resource;

import br.com.tmvolpato.mygames.common.constant.ConstantNumeric;
import br.com.tmvolpato.mygames.common.constant.ConstantQuery;
import br.com.tmvolpato.mygames.common.web.util.Mappings;
import br.com.tmvolpato.mygames.model.Game;
import br.com.tmvolpato.mygames.repository.game.filter.GameFilter;
import br.com.tmvolpato.mygames.service.game.GameService;
import br.com.tmvolpato.mygames.service.security.UserApplication;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Game resource.
 *
 * @author Thiago Michel Volpato
 * @since 2017
 * @version 1.0.0
 */
@RestController
@RequestMapping(value = Mappings.GAMES, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Game", description = "Game Resource")
public class GameResource extends AbstractResource<Game> {

    private final GameService gameService;

    @Autowired
    public GameResource(final GameService gameService) {
        super(Game.class);

        this.gameService = gameService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER') and #oauth2.hasScope('write')")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add a new game")
    public void create(@ApiIgnore @AuthenticationPrincipal final UserApplication userApplication,
                       @ApiParam(type = "body", name = "game", required = true)
                       @Valid @RequestBody final Game game, final UriComponentsBuilder uriBuilder,
                       final HttpServletResponse response) {
        this.checkRequiredCreateInternal(game);
        final Game gameSaved = this.gameService.create(userApplication, game);
        this.createPublishEvent(gameSaved, uriBuilder, response);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER') and #oauth2.hasScope('write')")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update the game")
    public void update(@ApiIgnore @AuthenticationPrincipal final UserApplication userApplication,
                       @ApiParam(type = "path", name = "id", required = true)
                       @PathVariable("id") final Long id,
                       @ApiParam(type = "body", name = "game", required = true)
                       @Valid @RequestBody final Game game) {
        this.checkRequiredUpdateInternal(id, game);
        this.gameService.update(userApplication, game);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER') and #oauth2.hasScope('write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete the game")
    public void delete(@ApiIgnore @AuthenticationPrincipal final UserApplication userApplication,
                       @ApiParam(type = "path", name = "id", required = true)
                       @PathVariable("id") final Long id) {
        this.checkRequiredPrimaryKeyDeleteInternal(id);
        this.gameService.delete(userApplication, id);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER') and #oauth2.hasScope('read')")
    @ApiOperation(value = "Find of game by id")
    public ResponseEntity<Game> findByGameId(@ApiIgnore @AuthenticationPrincipal final UserApplication userApplication,
                                             @ApiParam(type = "path", name = "id", required = true)
                                             @PathVariable("id") final Long id, final UriComponentsBuilder uriBuilder,
                                             final HttpServletResponse response) {

        final Optional<Game> gameFound = this.gameService.findById(userApplication, id);
        this.checkRequiredSingleResourceInternal(gameFound.get());
        this.singlePublishEvent(uriBuilder, response);
        return ResponseEntity.status(HttpStatus.OK)
                .cacheControl(CacheControl.maxAge(ConstantNumeric.ONE_HUNDRED, TimeUnit.SECONDS))
                .body(gameFound.get());
    }

    @GetMapping(params = { ConstantQuery.PAGE, ConstantQuery.SIZE })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER') and #oauth2.hasScope('read')")
    @ApiOperation(value = "List of games with filter and paginated")
    public ResponseEntity<List<Game>> findAllPaginatedAndFilter(@ApiIgnore @AuthenticationPrincipal final UserApplication userApplication,
                                                                final GameFilter gameFilter,
                                                                @ApiParam(type = "query", name = ConstantQuery.PAGE, required = true)
                                                                @RequestParam(value = ConstantQuery.PAGE) final int page,
                                                                @ApiParam(type = "query", name = "size", required = true)
                                                                @RequestParam(value = ConstantQuery.SIZE) final int size,
                                                                final UriComponentsBuilder uriBuilder,
                                                                final HttpServletResponse response) {

        final Page<Game> resultPages = this.gameService.findAllPaginatedAndFilter(userApplication, gameFilter, page, size);
        this.checkRequiredPaginatedResourceInternal(page, resultPages);
        this.paginatedPublishEvent(page, size, resultPages.getTotalPages(), uriBuilder, response);
        return ResponseEntity.status(HttpStatus.OK)
                .cacheControl(CacheControl.maxAge(ConstantNumeric.ONE_HUNDRED, TimeUnit.SECONDS))
                .body(Lists.newArrayList(resultPages.getContent()));
    }
}
