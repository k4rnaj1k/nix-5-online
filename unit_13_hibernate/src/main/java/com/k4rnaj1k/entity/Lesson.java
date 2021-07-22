package com.k4rnaj1k.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="group_id")
    @Access(AccessType.PROPERTY)
    private Group group;

    @OneToOne
    private Theme theme;

    @OneToOne
    @Access(AccessType.PROPERTY)
    private Teacher teacher;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
        teacher.addLesson(this);
    }

    public Long getId() {
        return id;
    }


    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
        group.addLesson(this);
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", time=" + time +
                ", {group=" + group.getGroup_name() + ", group_id="+ group.getGroup_id() +
                "}, theme=" + theme +
                ", teacher=" + teacher +
                '}';
    }
}
