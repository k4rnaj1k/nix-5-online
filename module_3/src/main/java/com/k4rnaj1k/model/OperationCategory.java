package com.k4rnaj1k.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class OperationCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Type type;

    public enum Type{
        INCOME,
        EXPENCE
    }

    private String type_name;

}
