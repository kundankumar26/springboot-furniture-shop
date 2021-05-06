package com.example.furnitureshop.models;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class TempUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tempId;

    private long empId;

    private String empFirstName;

    private String empLastName;

    private String username;

    @Email
    private String email;

    private String empPassword;

    private boolean isEnabled;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
