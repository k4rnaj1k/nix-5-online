package com.k4rnaj1k;

import javax.persistence.*;
import java.util.List;

@Table(name="courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long course_id;

    @Column(name="course_name")
    private String courseName;

    @OneToMany(mappedBy = "course_id")
    private List<Group> groups;
}
