package com.k4rnaj1k.model;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "operations")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long operation_id;

    private Instant time;

    @ManyToOne
    private OperationCategory category;

    private Long sum;
}
