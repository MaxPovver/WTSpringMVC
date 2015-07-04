package com.maxpovver.worktracker;

import com.maxpovver.worktracker.respositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 04.07.15.
 */

@Service
public class DBUtility {

    public static UserRepository rep;
    public UserRepository users() {
        return rep;//(UserRepository)ctx.getBean("userRepository");
    }

    @Autowired
    private void setUserRepository(UserRepository r) {
        rep = r;
    }
    public static LogRepository logs() {
        return (LogRepository)ctx.getBean("logRepository");
    }

    public static JobRepository jobs() {
        return (JobRepository)ctx.getBean("jobRepository");
    }

    public static ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");

}
