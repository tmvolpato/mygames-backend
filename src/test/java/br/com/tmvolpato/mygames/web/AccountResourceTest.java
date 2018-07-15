package br.com.tmvolpato.mygames.web;

import br.com.tmvolpato.mygames.common.constant.ConstantNumeric;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test resource for new account.
 *
 * @author Thiago Michel Volpato
 * @since 2018
 * @version 1.0.0
  */
@RunWith(SpringRunner.class)
@WebMvcTest(value = AccountResource.class, secure = false)
public class AccountResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void createShouldNewAccount() throws Exception {
        final String userJson = "{\"name\": \"Maria\", \"email\": \"maria@email.com\", \"password\": \"12345\"}";

        when(this.userService.create(eq(null), any(User.class))).thenReturn(this.newUser());

        this.mockMvc.perform(post("/api/public/create-account")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void createNewAccountWhenObjectJsonIsEmptyShouldThrownBadRequest() throws Exception {
        final String userJson = "{}";

        when(this.userService.create(eq(null), any(User.class))).thenReturn(this.newUser());

        this.mockMvc.perform(post("/api/public/create-account")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messageapierror", notNullValue()))
                .andExpect(jsonPath("$.messageapierror.status", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.messageapierror.message", is("Validation error")))
                .andExpect(jsonPath("$.messageapierror.messageSubErrors", hasSize(ConstantNumeric.THREE)))
                .andExpect(jsonPath("$.messageapierror.messageSubErrors[0].object", is("user")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void createNewAccountWhenNameIsNullShouldThrownBadRequest() throws Exception {
        final String userJson = "{\"name\": null, \"email\": \"maria@email.com\", \"password\": \"12345\"}";

        when(this.userService.create(eq(null), any(User.class))).thenReturn(this.newUser());

        this.mockMvc.perform(post("/api/public/create-account")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messageapierror", notNullValue()))
                .andExpect(jsonPath("$.messageapierror.status", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.messageapierror.message", is("Validation error")))
                .andExpect(jsonPath("$.messageapierror.messageSubErrors", hasSize(ConstantNumeric.ONE)))
                .andExpect(jsonPath("$.messageapierror.messageSubErrors[0].object", is("user")))
                .andExpect(jsonPath("$.messageapierror.messageSubErrors[0].field", is("name")))
                .andExpect(jsonPath("$.messageapierror.messageSubErrors[0].message", is("name.not.blank")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void createNewAccountWhenNameIsEmptyShouldThrownBadRequest() throws Exception {
        final String userJson = "{\"name\": \" \", \"email\": \"maria@email.com\", \"password\": \"12345\"}";

        when(this.userService.create(eq(null), any(User.class))).thenReturn(this.newUser());

        this.mockMvc.perform(post("/api/public/create-account")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messageapierror", notNullValue()))
                .andExpect(jsonPath("$.messageapierror.status", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.messageapierror.message", is("Validation error")))
                .andExpect(jsonPath("$.messageapierror.messageSubErrors", hasSize(ConstantNumeric.ONE)))
                .andExpect(jsonPath("$.messageapierror.messageSubErrors[0].object", is("user")))
                .andExpect(jsonPath("$.messageapierror.messageSubErrors[0].field", is("name")))
                .andExpect(jsonPath("$.messageapierror.messageSubErrors[0].rejectedValue", is(" ")))
                .andExpect(jsonPath("$.messageapierror.messageSubErrors[0].message", is("name.not.blank")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void createNewAccountWhenEmailIsNullShouldThrownBadRequest() throws Exception {
        final String userJson = "{\"name\": \"Maria\", \"email\": \"maria@\", \"password\": \"12345\"}";

        when(this.userService.create(eq(null), any(User.class))).thenReturn(this.newUser());

        this.mockMvc.perform(post("/api/public/create-account")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messageapierror", notNullValue()))
                .andExpect(jsonPath("$.messageapierror.status", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.messageapierror.message", is("Validation error")))
                .andExpect(jsonPath("$.messageapierror.messageSubErrors", hasSize(ConstantNumeric.ONE)))
                .andExpect(jsonPath("$.messageapierror.messageSubErrors[0].object", is("user")))
                .andExpect(jsonPath("$.messageapierror.messageSubErrors[0].field", is("email")))
                .andExpect(jsonPath("$.messageapierror.messageSubErrors[0].rejectedValue", is("maria@")))
                .andExpect(jsonPath("$.messageapierror.messageSubErrors[0].message", is("email.validate")))
                .andDo(MockMvcResultHandlers.print());
    }

    private User newUser() {
        final User user = new User();
        user.setId(1L);
        user.setName("Maria");
        user.setEmail("maria@email.com");
        user.setPassword("12345");
        return user;
    }
}
