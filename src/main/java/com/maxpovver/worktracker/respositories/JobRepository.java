package com.maxpovver.worktracker.respositories;

import com.maxpovver.worktracker.entities.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by admin on 04.07.15.
 */

public interface JobRepository extends CrudRepository<Job, Long> {
}
