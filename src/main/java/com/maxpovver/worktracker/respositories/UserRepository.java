package com.maxpovver.worktracker.respositories;

import com.maxpovver.worktracker.entities.*;
import org.springframework.data.repository.*;

import java.util.List;

/**
 * Created by admin on 04.07.15.
 */

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByUsername(String username);
}
