package com.k4rnaj1k.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String email;

    private String username;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts = new ArrayList<>();
}
