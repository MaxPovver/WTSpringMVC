package com.maxpovver.worktracker.repositories;

import com.maxpovver.worktracker.entities.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by admin on 05.07.15.
 */
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role getRoleByRole(String role);
}
