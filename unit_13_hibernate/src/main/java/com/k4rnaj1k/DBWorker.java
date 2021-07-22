package com.k4rnaj1k;

import com.k4rnaj1k.entity.*;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DBWorker {

    private final Session session;

    public DBWorker(Session session) {
        this.session = session;
    }

    public void init(Session session) {
        Course course = new Course();
        course.setCourse_name("java");
        session.persist(course);

        Group group = new Group();
        group.setGroup_name("nix 5 online");
        group.setCourse(course);
        session.persist(group);

        Student student = new Student();
        student.setName("Vlad");
        student.setSurname("Liasota");
        student.setGroup(group);
        session.persist(student);

        Teacher teacher = new Teacher();
        teacher.setName("Michail");
        teacher.setSurname("Surname");
        session.persist(teacher);

        Lesson lesson = new Lesson();
        lesson.setTeacher(teacher);
        lesson.setLessons_group(group);
        lesson.setTime(new Date());
        session.persist(lesson);


        Theme theme = new Theme();
        theme.setName("jpa/jdbc");
        lesson.setTheme(theme);
        session.persist(theme);

        Grade grade = new Grade();
        grade.setLesson(lesson);
        grade.setStudent(student);
        grade.setGrade(((byte) 10));
        session.persist(grade);
    }

    private List<Student> getStudents() {
        Query<Student> listStudents = session.createQuery("from Student", Student.class);
        return listStudents.list();
    }

    public void printStudents() {
        List<Student> students = getStudents();
        System.out.println("Current students list:");
        System.out.println("id\tname\tsurname");
        for (Student s :
                students) {
            System.out.println(s.getId() + "|\t" + s.getName() + "|\t" + s.getSurname());
        }
    }

    public Student getStudent(String name, String surname) {
        List<Student> students = getStudents();
        Student student = null;
        for (Student s :
                students) {
            if (s.getName().equals(name) && s.getSurname().equals(surname)) {
                student = s;
                break;
            }
        }
        if (student != null) {
            System.out.println("Closest lesson for student " + name + " " + surname + " " + student.getId());
            System.out.println(student.getLessons().stream().sorted(Comparator.comparing(Lesson::getTime)).toList().get(0));
        } else {
            System.out.println("Couldn't find student in db, please try again.");
        }
        return student;
    }
}
