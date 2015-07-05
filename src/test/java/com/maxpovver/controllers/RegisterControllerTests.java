package com.maxpovver.controllers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Created by Maxim Chistov on 05.07.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@EnableAutoConfiguration
public class RegisterControllerTests {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void getAccount() throws Exception {
        /*this.mockMvc.perform(get("/accounts/1").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("Lee"));*/
        RegisterController.RegTry dummy = new RegisterController.RegTry();
        //should reject with diffrent pass and confirm
        dummy.setUsername("max");
        dummy.setPassword("123");
        dummy.setPassword_confirm("42");
        this.mockMvc.perform(post("/register", dummy, model()))
                .andExpect(redirectedUrl("error"));
        //should reject any empty field
        dummy.setUsername("");//empty uname
        dummy.setPassword("123");
        dummy.setPassword_confirm("123");
        this.mockMvc.perform(post("/register",dummy, model()))
                .andExpect(redirectedUrl("error"));
        dummy.setUsername("dsd");
        dummy.setPassword("");//empty pass
        dummy.setPassword_confirm("42");
        this.mockMvc.perform(post("/register",dummy, model()))
                .andExpect(redirectedUrl("error"));
        dummy.setUsername("443");
        dummy.setPassword("123");
        dummy.setPassword_confirm("");//empty confirm
        this.mockMvc.perform(post("/register",dummy, model()))
                .andExpect(redirectedUrl("error"));
        //should pass
        dummy.setUsername("max");
        dummy.setPassword("123");
        dummy.setPassword_confirm("123");
        this.mockMvc.perform(post("/register",dummy, model()))
                .andExpect(redirectedUrl("login"));
        //should the db-related tests be here?
        //add user two times - first should succeed and second should fail
        //here ?
    }

}