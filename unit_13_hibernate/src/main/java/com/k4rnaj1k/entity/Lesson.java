package com.k4rnaj1k.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lesson_id;

    private Date time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group lessons_group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id")
    private Theme theme;

    @OneToOne
    @Access(AccessType.PROPERTY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
        teacher.addLesson(this);
    }

    public Long getLesson_id() {
        return lesson_id;
    }


    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Group getLessons_group() {
        return lessons_group;
    }

    public void setLessons_group(Group lessons_group) {
        this.lessons_group = lessons_group;
        lessons_group.addLesson(this);
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
        theme.addLesson(this);
    }


    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + lesson_id +
                ", time=" + time +
                ", {group=" + lessons_group.getGroup_name() + ", group_id=" + lessons_group.getGroup_id() +
                "}, theme=" + theme +
                ", teacher=" + teacher +
                '}';
    }
}
