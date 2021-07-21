package com.k4rnaj1k;

import javax.persistence.*;
import java.util.Date;

@Table(name="lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long class_id;

    private Date time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="group_id")
    @Access(AccessType.PROPERTY)
    private Group group;

}
