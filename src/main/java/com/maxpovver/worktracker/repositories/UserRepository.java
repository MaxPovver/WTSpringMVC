package com.maxpovver.worktracker.repositories;

import com.maxpovver.worktracker.entities.*;
import org.springframework.data.repository.*;

/**
 * Created by admin on 04.07.15.
 */

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
