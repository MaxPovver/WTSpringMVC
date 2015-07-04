package com.maxpovver.worktracker.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

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
    private List<Job> jobs;

    @ManyToMany
    @Lazy(value = false)
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

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

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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
