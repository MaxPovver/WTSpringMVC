package com.maxpovver.worktracker.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by admin on 03.07.15.
 */
@Entity
@Table(name = "logs")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "start_time")
    private Timestamp startTime;
    @Column(name = "end_time")
    private Timestamp endTime;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="job_id", nullable=false)
    private Job job;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Log(Timestamp startTime, Timestamp endTime, Job job) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.job = job;
    }

    protected Log() {
    }

    @Override
    public String toString() {
        return "Log[" + startTime + " : " + endTime + "]";
    }

    public Double getHours()
    {
        return getDiff()/1000.0 //milisecs -> secs
                /3600.0; //secs -> hours
    }

    public long getDiff()
    {
        return endTime.getTime() - startTime.getTime();
    }
}
