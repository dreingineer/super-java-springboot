package com.company.user;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    private Integer id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
