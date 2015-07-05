package com.maxpovver.controllers;

import com.maxpovver.worktracker.entities.Role;
import com.maxpovver.worktracker.entities.User;
import com.maxpovver.worktracker.respositories.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.maxpovver.worktracker.utils.DBUtility.users;
import com.maxpovver.worktracker.entities.User;
import org.springframework.web.servlet.View;

/**
 * Created by admin on 05.07.15.
 */
@Controller
@PreAuthorize("isAnonymous() or hasRole('ADMIN')")
public class RegisterController {
    static class RegTry {
        public String username;
        public String password;
        public String password_confirm;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPassword_confirm() {
            return password_confirm;
        }

        public void setPassword_confirm(String password_confirm) {
            this.password_confirm = password_confirm;
        }

        public boolean empty()
        {
            return username.isEmpty() || password.isEmpty() || password_confirm.isEmpty();
        }
        public RegTry(){username=""; password=""; password_confirm="";}
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String tryRegisterUser(RegTry trying, Model model){
        if (trying == null || trying.empty())
        {
            return "/register";
        }
        if (!trying.password.equals(trying.password_confirm))
        {
            model.addAttribute("error","passwords don't match");
        } else
        if (users().findByUsername(trying.username) != null)
        {
            model.addAttribute("error","User with this name exists!");
        }
        if (model.containsAttribute("error"))
        {
            return "/register";
        }
        User user = new User(trying.username, trying.password);
        List<Role> roles = new ArrayList<>();
        roles.add(Role.USER());
        user.setRoles(roles);
        user = users().save(user);
        if (user == null)
        {
            model.addAttribute("error","db failed to add your user");
            return "/register";
        }
        return "/login";
    }
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showForm() {
        return "/register";
    }
}
