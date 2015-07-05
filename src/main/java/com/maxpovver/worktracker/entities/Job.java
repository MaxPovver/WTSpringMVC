package com.maxpovver.worktracker.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maxpovver.worktracker.utils.WorkdaysService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
    @JsonIgnore
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

    /**
     * Counts hours you've worked in current month.
     * Might work slower if it initiates lazy load.
     * @return hours worked in current month
     */
    @JsonIgnore
    public double getHours()
    {
        return getHours(Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.YEAR));
    }
    /**
     * Counts hours you've worked in some month.
     * Might work slower if it initiates lazy load.
     * @param month 1-12 month we get hours for
     * @param year year we get hours for
     * @return hours worked in current month
     */
    @JsonIgnore
    public double getHours(int year, int month)
    {
        return getLogs().stream()
                .filter(l->l.getStartTime().getMonth() == month
                        && l.getStartTime().getYear() == year)
                .mapToLong(Log::getDiff).sum() / 1000.0 / 3600.0;
    }

    /**
     * Counts how many you've earned for current month.
     * Notice that counting speed differs because of lazy load.
     * @return how many you've earned for current month
     */
    @JsonIgnore
    public Double getCurrentSalary()
    {   //TODO: fetch workhours from db!!!!
        return getSalary() * (getHours()/ (WorkdaysService.get().getWorkdays() * 8.0));
    }
}
