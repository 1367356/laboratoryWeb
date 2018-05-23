package com.li.pojo;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 实验室网站用户
 */
public class User {
    public int id;
    public String userName;
    public String password;
    public String roles;
    public String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Set<String> getRolesSet() {
        if (null == roles) {
            return null;
        }
        return Collections.unmodifiableSet(
                new HashSet<String>(Arrays.asList(getRoles().split(","))));
    }

    public void addRole(String role) {
        String currRoles = this.getRoles();
        if (null == currRoles || this.getRoles().contains(role)) {
            return;
        }
        this.setRoles(currRoles + "," + role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", roles='" + roles + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
