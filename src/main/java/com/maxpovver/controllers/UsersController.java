package com.maxpovver.controllers;

import static com.maxpovver.worktracker.DBUtility.*;

import com.maxpovver.worktracker.DBUtility;
import com.maxpovver.worktracker.entities.*;
import com.maxpovver.worktracker.respositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by admin on 04.07.15.
 */
@RestController
public class UsersController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/users/add")
    public User addUser(@RequestParam(value="name") String name, @RequestParam(value="password") String password) {
       UserRepository rep = users();
        return rep.save(new User(name, password));
    }

    @RequestMapping("/users")
    public List<User> getUsers(){
        UserRepository rep = users();
        List<User> res = new ArrayList<>();
        rep.findAll().forEach(res::add);
        return res;
    }
}
