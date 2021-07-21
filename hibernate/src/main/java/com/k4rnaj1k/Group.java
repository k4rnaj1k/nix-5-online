package com.k4rnaj1k;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long group_id;

    @Column(nullable = false)
    private String group_name;

    @OneToMany(mappedBy = "group")
    @JoinColumn(name = "group_id")
    private List<Student> students;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Long course;


}
