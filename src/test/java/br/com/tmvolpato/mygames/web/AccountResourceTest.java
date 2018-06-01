package br.com.tmvolpato.mygames.web;

import br.com.tmvolpato.mygames.model.User;
import br.com.tmvolpato.mygames.resource.AccountResource;
import br.com.tmvolpato.mygames.service.User.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test resource for new account.
 *
 * @author Thiago Michel Volpato
 * @version 1.0.0
 * @sice 2018
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = AccountResource.class, secure = false)
public class AccountResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void saveShouldCreateNewAccount() throws Exception {
        final User user = new User();
        user.setId(1L);
        user.setName("Maria");
        user.setEmail("maria@email.com");
        user.setPassword("12345");

        String userJson = "{\"name\": \"Maria\", \"email\": \"maria@email.com\", \"password\": \"12345\"}";

        when(this.userService.create(eq(null), any(User.class))).thenReturn(user);

        this.mockMvc.perform(post("/api/public/create-account")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isCreated());
    }
}
