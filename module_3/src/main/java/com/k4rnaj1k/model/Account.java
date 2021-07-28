package com.k4rnaj1k.model;

import javax.persistence.*;

@Entity
@Table(name="accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long account_id;

    private Long balance;

    @ManyToOne
    private User user;
}
