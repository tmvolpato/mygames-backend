package br.com.tmvolpato.mygames.resource;

import br.com.tmvolpato.mygames.common.constant.ConstantNumeric;
import br.com.tmvolpato.mygames.common.web.util.Mappings;
import br.com.tmvolpato.mygames.model.User;
import br.com.tmvolpato.mygames.service.User.UserService;
import br.com.tmvolpato.mygames.service.security.UserApplication;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * User resource.
 *
 * @author Thiago Michel Volpato
 * @since 2018
 * @version 1.0.0
 */
@RestController
@RequestMapping(value = Mappings.USERS, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "User", description = "User Resource")
public class UserResource extends AbstractResource<User>{

    private final UserService userService;

    @Autowired
    public UserResource(final UserService userService) {
        super(User.class);

        this.userService = userService;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER') and #oauth2.hasScope('write')")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update a user")
    public void update(@ApiIgnore @AuthenticationPrincipal final UserApplication userApplication,
                       @ApiParam(type = "path", name= "id", required = true)
                       @PathVariable("id") final Long id,
                       @ApiParam(type = "body", name = "user", required = true)
                       @Valid @RequestBody User user) {
        this.checkRequiredUpdateInternal(id, user);
        this.userService.update(userApplication, user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER') and #oauth2.hasScope('write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete the user")
    public void delete(@ApiIgnore @AuthenticationPrincipal final UserApplication userApplication,
                       @ApiParam(type = "path", name = "id", required = true)
                       @PathVariable("id") final Long id) {
       this.checkRequiredPrimaryKeyDeleteInternal(id);
       this.userService.delete(userApplication, id);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER') and #oauth2.hasScope('read')")
    @ApiOperation(value = "Find user by id")
    public ResponseEntity<User> findByUserId(@ApiIgnore @AuthenticationPrincipal final UserApplication userApplication,
                                             @ApiParam(type = "path", name = "id", required = true)
                                             @PathVariable("id") final Long id, final UriComponentsBuilder uriBuilder,
                                             final HttpServletResponse response) {

        final Optional<User> userFound = this.userService.findById(userApplication, id);
        this.checkRequiredSingleResourceInternal(userFound.get());
        this.singlePublishEvent(uriBuilder, response);
        return ResponseEntity.status(HttpStatus.OK)
                .cacheControl(CacheControl.maxAge(ConstantNumeric.ONE_HUNDRED, TimeUnit.SECONDS))
                .body(userFound.get());
    }
}
