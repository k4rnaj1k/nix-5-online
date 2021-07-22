package com.k4rnaj1k;

import javax.persistence.*;
import java.util.List;

@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Lesson> lessons;
}
