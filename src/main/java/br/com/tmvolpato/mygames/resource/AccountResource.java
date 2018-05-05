package br.com.tmvolpato.mygames.resource;

import br.com.tmvolpato.mygames.common.web.util.Mappings;
import br.com.tmvolpato.mygames.model.User;
import br.com.tmvolpato.mygames.service.User.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Account resource.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @since 2017
 */
@RestController
@RequestMapping(value = Mappings.CREATE_ACCOUNT)
@Api(value = "Create Account", description = "Create a new account")
public class AccountResource extends AbstractResource<User> {

    private final UserService userService;

    @Autowired
    public AccountResource(final UserService userService) {
        super(User.class);

        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Create a new account")
    public void create(@ApiParam(type = "body", name = "user", required = true)
                       @Valid @RequestBody  final User user,
                       final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {

        this.checkRequiredCreateInternal(user);
        final User userSaved = this.userService.create(null, user);
        this.createPublishEvent(userSaved, uriBuilder, response);
    }
}
