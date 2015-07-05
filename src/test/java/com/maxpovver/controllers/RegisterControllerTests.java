package com.maxpovver.controllers;
import com.maxpovver.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static com.maxpovver.controllers.RegisterController.RegTry;
/**
 * Created by Maxim Chistov on 05.07.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
//@TestPropertySource(properties = { "timezone = GMT", "port: 4242" }) TODO: run test on tests db set here
public class RegisterControllerTests {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception{
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test//(expected = AuthenticationCredentialsNotFoundException.class)
    //@PreAuthorize("authenticated")
    @WithMockUser(username = "testuser", password = "empty", roles={"ADMIN","USER"})
    public void lightTests() throws Exception {
        //no paras - so show register form
        this.mockMvc.perform(get("/register"))
                .andExpect(view().name("/register"));
        //should reject with diffrent pass and confirm
        this.mockMvc.perform(post("/register")
                .param("username","max")
                .param("password", "123")
                .param("password_confirm","42"))
                .andExpect(redirectedUrl("/error"));
        //should reject any empty field
        this.mockMvc.perform(post("/register")
                .param("username", "")//empty login
                .param("password", "123")
                .param("password_confirm", "123"))
                .andExpect(view().name("/register"));
        this.mockMvc.perform(post("/register")
                .param("username","max")//empty pass & confirm(if one is not empty they won't pass first test)
                .param("password", "")
                .param("password_confirm",""))
                .andExpect(view().name("/register"));
    }
    @Test
    @Transactional
    @Rollback
    @WithMockUser(username = "testuser", password = "empty", roles={"ADMIN","USER"})
    public void heavyTests() throws Exception {
        //should pass
        this.mockMvc.perform(post("/register")
                .param("username","max424234234235254524")
                .param("password","123")
                .param("password_confirm","123"))
                .andExpect(redirectedUrl("/login"));
        //add user two times - first should succeed and second should fail
        this.mockMvc.perform(post("/register")
                .param("username","max424234234235254524")
                .param("password","123")
                .param("password_confirm","123"))
                .andExpect(redirectedUrl("/error"));
    }
}