package com.maxpovver.worktracker.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maxpovver.worktracker.DBUtility;
import org.hibernate.Hibernate;
import org.hibernate.LazyInitializationException;
import org.hibernate.collection.internal.PersistentBag;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.Collection;

import static java.util.stream.Collectors.joining;

/**
 * Created by admin on 03.07.15.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String username;
    @JsonIgnore
    private String password;
    @OneToMany(cascade=CascadeType.ALL, mappedBy="user")
    private Collection<Job> jobs;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public Collection<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Collection<Job> jobs) {
        this.jobs = jobs;
    }

    public User(String username, String password) {
        this.password = password;
        this.username = username;
    }

    protected User() {
    }

    @Override
    public String toString() {
            return "[" + getUsername() + "=" + getPassword() + ", "
                    + getJobs().stream().map(Job::toString).collect(joining(", ")) + "]";
    }
}
