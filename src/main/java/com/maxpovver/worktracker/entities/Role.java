package com.maxpovver.worktracker.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.text.CollationElementIterator;
import java.util.Collection;
import java.util.List;

/**
 * Created by admin on 05.07.15.
 */
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    private int id;
    private String role;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @JsonIgnore
    @Override
    public String getAuthority() {
        return getRole();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    protected Role(){}
}
