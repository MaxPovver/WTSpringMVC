package com.maxpovver.worktracker.utils;

import com.maxpovver.worktracker.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 04.07.15.
 */

@Service
public class DBUtility {

    private static UserRepository urep;
    public static UserRepository users() {
        return urep;//(UserRepository)ctx.getBean("userRepository");
    }

    @Autowired
    private void setUserRepository(UserRepository r) {
        urep = r;
    }

    private static JobRepository jrep;
    public static JobRepository jobs() {
        return jrep;
    }

    @Autowired
    private void setJobRepository(JobRepository r) {
        jrep = r;
    }

    private static LogRepository lrep;
    public static LogRepository logs() {
        return lrep;
    }

    @Autowired
    private void setLogRepository(LogRepository r) {
        lrep = r;
    }

    private static RoleRepository rrep;
    public static RoleRepository roles() {
        return rrep;
    }

    @Autowired
    private void setRoleRepository(RoleRepository r) {
        rrep = r;
    }
}
