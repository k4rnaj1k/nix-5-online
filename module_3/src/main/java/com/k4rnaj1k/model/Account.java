package com.k4rnaj1k.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long account_id;

    private Long balance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "account")
    private List<Operation> operations = new ArrayList<>();

    private String name;

    public Account() {
    }

    public Account(Long balance, String name, User user) {
        this.balance = balance;
        this.user = user;
        this.name = name;
    }

    public Long getAccount_id() {
        return account_id;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        user.addAccount(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public void addOperation(Operation operation) {
        this.operations.add(operation);
        if (operation.getCategory().getType() == OperationCategory.Type.INCOME) {
            this.balance += operation.getSum();
        } else {
            this.balance -= operation.getSum();
        }
    }
}
