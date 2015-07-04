package com.maxpovver.worktracker.utils;

import com.maxpovver.worktracker.respositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
        return jrep;//(UserRepository)ctx.getBean("userRepository");
    }

    @Autowired
    private void setJobRepository(JobRepository r) {
        jrep = r;
    }

    private static LogRepository lrep;
    public static LogRepository logs() {
        return lrep;//(UserRepository)ctx.getBean("userRepository");
    }

    @Autowired
    private void setLogRepository(LogRepository r) {
        lrep = r;
    }
}
