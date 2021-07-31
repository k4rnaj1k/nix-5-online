package com.k4rnaj1k.model;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "operations")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant time = Instant.now();

    @ManyToOne
    private OperationCategory category;

    private Long sum;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Operation() {
    }

    public Operation(OperationCategory category, Long sum) {
        this.category = category;
        setSum(sum);
    }

    public Operation(OperationCategory category, Long sum, Account account) {
        this.category = category;
        setSum(sum);
        setAccount(account);
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public OperationCategory getCategory() {
        return category;
    }

    public void setCategory(OperationCategory category) {
        this.category = category;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        if (sum <= 0) {
            throw new IllegalArgumentException("The operation's sum can't be less or equal to 0");
        } else {
            this.sum = sum;
        }
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
        account.addOperation(this);
    }
}
