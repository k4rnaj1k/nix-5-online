package com.k4rnaj1k.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "operation_categories")
public class OperationCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        INCOME,
        EXPENCE;
    }

    private String type_name;

    @OneToMany(mappedBy = "category")
    private List<Operation> operations = new ArrayList<>();

    public OperationCategory(){}

    public OperationCategory(Type type, String type_name) {
        this.type = type;
        this.type_name = type_name;
    }

    public Long getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}
