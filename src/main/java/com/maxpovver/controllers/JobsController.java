package com.maxpovver.controllers;

import com.maxpovver.worktracker.entities.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.maxpovver.worktracker.repositories.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.maxpovver.worktracker.utils.DBUtility.*;

/**
 * Created by admin on 04.07.15.
 */
@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
@Controller
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
    static class LogTry
    {
        String startTime, endTime;
        public LogTry()
        {
            startTime = "";
            endTime = "";
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

    }

    /**
     * Tries to add log into logs list of the job if it is allowed.
     * @param userDetails needed to get accessible jobs for logged in user
     * @param jobID job user tries to log to
     * @param lt log form data
     * @return not null if adding was successful
     */
    @RequestMapping(value = "jobs/{jobID}/logs", method = RequestMethod.POST)
    @ResponseBody
    public Log tryAddLog(@AuthenticationPrincipal UserDetails userDetails,
                         @PathVariable long jobID, LogTry lt)
    {
        List<Job> jobs = getJobs(userDetails);
        if(jobs == null)
        {
            return null;
        }
        Job forJob = jobs.stream().filter(job -> job.getId() == jobID).findFirst().orElse(null);
        if (forJob == null)
        {
            return null;
        }
        //TODO: should be processed by service, not from local code!
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Timestamp start=null,end=null;
        try {
            start = new Timestamp(format.parse(lt.startTime).getTime());
            end = new Timestamp(format.parse(lt.endTime).getTime());
        }catch (ParseException e){}
        return logs().save(new Log(start, end, forJob));
    }

    /**
     * Returns list of all the logs if user has permission for that
     * @param userDetails used for permissions check
     * @param jobID job for which logs will be searched
     * @return all logs for job
     */
    @RequestMapping(value = "jobs/{jobID}/logs", method = RequestMethod.GET)
    @ResponseBody
    public List<Log> tryGetLogs(@AuthenticationPrincipal UserDetails userDetails,
                         @PathVariable long jobID)
    {
        List<Job> jobs = getJobs(userDetails);
        if(jobs == null)
        {
            return null;
        }
        Job forJob = jobs.stream().filter(job -> job.getId() == jobID).findFirst().orElse(null);
        if (forJob == null)
        {
            return null;
        }
        return forJob.getLogs();
    }

    /**
     * Used to display job log add form
     * @param userDetails to check permissions
     * @param jobID to check job
     * @return template type
     */
    @RequestMapping(value = "jobs/{jobID}/logs/add", method = RequestMethod.GET)
    public String addLog(@AuthenticationPrincipal UserDetails userDetails,
                                @PathVariable long jobID, Model m)
    {
        List<Job> jobs = getJobs(userDetails);
        if(jobs == null)
        {
            return "redirect:/error";
        }
        Job forJob = jobs.stream().filter(job -> job.getId() == jobID).findFirst().orElse(null);
        if (forJob == null)
        {
            return "redirect:/error";
        }
        m.addAttribute("job", forJob);
        return "/add";
    }
}
