package com.maxpovver.worktracker.repositories;

import com.maxpovver.worktracker.entities.*;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by admin on 04.07.15.
 */

public interface JobRepository extends CrudRepository<Job, Long> {
}
