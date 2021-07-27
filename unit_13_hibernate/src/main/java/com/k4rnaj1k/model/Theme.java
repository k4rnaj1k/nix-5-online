package com.k4rnaj1k.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="themes")
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long theme_id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lesson_id")
    private List<Lesson> lessons = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public void addLesson(Lesson lesson){
        this.lessons.add(lesson);
    }

    public Long getTheme_id() {
        return theme_id;
    }

    @Override
    public String toString() {
        return "Theme{" +
                "id=" + theme_id +
                ", name='" + name + '\'' +
                '}';
    }
}
