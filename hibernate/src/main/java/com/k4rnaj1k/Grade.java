package com.k4rnaj1k;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="grades")
public class Grade {
    @Id
    private Long id;

    private Lesson lesson;

    private Student student;

    private Byte grade;
}
