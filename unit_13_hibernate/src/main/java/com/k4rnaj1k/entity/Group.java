package com.k4rnaj1k.entity;

import javax.persistence.*;
import java.util.ArrayList;
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
    private List<Student> students = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @Access(AccessType.PROPERTY)
    private Course course;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Lesson> lessons = new ArrayList<>();


    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Long getGroup_id() {
        return group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void addStudent(Student student){
        this.students.add(student);
        student.setGroup(this);
    }

    public void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
    }

}
