package com.maxpovver.worktracker.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.joining;

/**
 * Created by admin on 03.07.15.
 */
@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private Double salary;
    private String currency;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    @OneToMany(cascade=CascadeType.ALL, mappedBy="job")
    private List<Log> logs;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    public Job(String name, Double salary, String currency, User user) {
        this.name = name;
        this.salary = salary;
        this.currency = currency;
        this.user = user;
    }

    protected Job() {
    }

    @Override
    public String toString() {
        return "Job["+name+", "+salary+" "+currency+", "+
                getLogs().stream().map(Log::toString).collect(joining(","))+"]";
    }
}
