package com.maxpovver.worktracker.respositories;

import com.maxpovver.worktracker.entities.*;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by admin on 04.07.15.
 */

public interface LogRepository extends CrudRepository<Log, Long> {
}
