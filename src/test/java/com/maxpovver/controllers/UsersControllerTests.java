package com.maxpovver.controllers;

import com.maxpovver.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import java.sql.SQLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Maxim Chistov on 06.07.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
public class UsersControllerTests {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception{
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(username = "testuser", password = "empty", roles={"USER"})
    public void lightTests() throws Throwable{
        try {
            //this should throw AccessDenied exception as our user doesn't have ADMIN role
            this.mockMvc.perform(get("/users"));
        } catch (Exception e)
        {
            throw e.getCause();
        }
    }
    @Test
    @WithMockUser(username = "testuser", password = "empty", roles={"ADMIN"})
    public void lightTests2() throws Throwable {
        //this should return valid json because we have admin rights
            this.mockMvc.perform(get("/users"))
            .andExpect(status().is2xxSuccessful());
    }

    @Test(expected = SQLException.class)
    @Transactional
    @Rollback
    @WithMockUser(username = "testuser", password = "empty", roles={"ADMIN"})
    public void heavyTests() throws Throwable
    {
        try {
            //should first add user correctly, then throw db error
            this.mockMvc.perform(get("/users/add")
                    .param("username", "dfecevstbs")
                    .param("password", "any"))
                    .andExpect(status().isAccepted());
            this.mockMvc.perform(get("/users/add")
                    .param("username", "dfecevstbs")
                    .param("password", "any"));
        }catch (Exception e)
        {
            throw e.getCause().getCause().getCause();
        }
    }
}
