package br.com.tmvolpato.mygames.resource;

import br.com.tmvolpato.mygames.common.constant.ConstantNumeric;
import br.com.tmvolpato.mygames.common.web.util.Mappings;
import br.com.tmvolpato.mygames.model.User;
import br.com.tmvolpato.mygames.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = Mappings.USERS)
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
    public void update(@PathVariable("id") final Long id, @Valid @RequestBody User user) {
        this.checkRequiredUpdateInternal(id, user);
        this.userService.update(this.getUserLogged(), user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER') and #oauth2.hasScope('write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") final Long id) {
       this.checkRequiredPrimaryKeyDeleteInternal(id);
       this.userService.delete(this.getUserLogged(), id);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER') and #oauth2.hasScope('read')")
    public ResponseEntity<User> findByUserId(@PathVariable("id") final Long id, final UriComponentsBuilder uriBuilder,
                                             final HttpServletResponse response) {

        final Optional<User> userFound = this.userService.findById(null, id);
        this.checkRequiredSingleResourceInternal(userFound.get());
        this.singlePublishEvent(uriBuilder, response);
        return ResponseEntity.status(HttpStatus.OK)
                .cacheControl(CacheControl.maxAge(ConstantNumeric.ONE_HUNDRED, TimeUnit.SECONDS))
                .body(userFound.get());
    }

    private User getUserLogged() {
        return this.userService.findUsername(super.getPrincipal()).get();
    }

}
