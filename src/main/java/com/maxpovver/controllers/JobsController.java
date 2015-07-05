package com.maxpovver.controllers;

import com.maxpovver.worktracker.entities.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.maxpovver.worktracker.respositories.*;

import java.util.ArrayList;
import java.util.List;

import static com.maxpovver.worktracker.utils.DBUtility.*;

/**
 * Created by admin on 04.07.15.
 */
@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
@RestController
public class JobsController {

    @RequestMapping("/jobs")
    public List<Job> getJobs(@AuthenticationPrincipal UserDetails userDetails){
        JobRepository rep = jobs();
        UserRepository urep = users();
        List<Job> result = new ArrayList<>();
        /**
         * For administrator we show all the jobs.
         * For users we give only theirs' jobs.
         */
        if (userDetails.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ADMIN")))
        {
            rep.findAll().forEach(result::add);
        }
        else if (userDetails.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("USER")))
        {
            User user = urep.findByUsername(userDetails.getUsername());
            result = user.getJobs();
        }
        return result;
    }
}
