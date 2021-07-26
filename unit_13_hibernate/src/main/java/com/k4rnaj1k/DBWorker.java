package com.k4rnaj1k;

import com.k4rnaj1k.entity.*;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.*;
import java.util.function.Supplier;

public class DBWorker {

    private final Supplier<Session> persistence;

    public DBWorker(Supplier<Session> persistence) {
        this.persistence = persistence;
    }

    public void init() {
        Session session = persistence.get();
        Course course = new Course();
        course.setCourse_name("java");
        session.persist(course);

        Group group = new Group();
        group.setGroup_name("nix 5 online");
        group.setCourse(course);
        session.persist(group);

        Group group2 = new Group();
        group2.setGroup_name("nix 6 online");
        group2.setCourse(course);
        session.persist(group2);

        Student student = new Student();
        student.setName("Some");
        student.setSurname("Student");
        student.setGroup(group2);
        session.persist(student);

        Student author = new Student();
        author.setName("Vlad");
        author.setSurname("Liasota");
        author.setGroup(group);
        session.persist(author);

        Teacher teacher = new Teacher();
        teacher.setName("Some");
        teacher.setSurname("Teacher");
        session.persist(teacher);

        Lesson lesson = new Lesson();
        lesson.setTeacher(teacher);
        lesson.setLessons_group(group);
        lesson.setTime(new Date());
        session.persist(lesson);

        Lesson lesson2 = new Lesson();
        lesson2.setTeacher(teacher);
        lesson2.setLessons_group(group2);
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DATE, 27);
        lesson2.setTime(calendar.getTime());
        session.persist(lesson2);


        Theme theme = new Theme();
        theme.setName("jpa/jdbc");
        session.persist(theme);
        lesson.setTheme(theme);

        Theme theme2 = new Theme();
        theme2.setName("reflection");
        lesson2.setTheme(theme2);
        session.persist(theme2);

        Grade grade = new Grade();
        grade.setLesson(lesson);
        grade.setStudent(author);
        grade.setGrade(((byte) 10));
        session.persist(grade);
    }

    private List<Student> getStudents() {
        Session session = persistence.get();
        Query<Student> listStudents = session.createQuery("from Student", Student.class);
        return listStudents.list();
    }

    public void printStudents() {
        List<Student> students = getStudents();
        System.out.println("Current students list:");
        System.out.println("id\tname\tsurname\t\tgroup");
        for (Student s :
                students) {
            System.out.println(s.getStudent_id() + "|\t" + s.getName() + "|\t" + s.getSurname() + "|\t" + s.getGroup().getGroup_name());
        }
    }

    public void getStudentsClosestLesson(Long id) {
        List<Student> students = getStudents();
        Student student = null;
        for (Student s :
                students) {
            if (s.getStudent_id().equals(id)) {
                student = s;
                break;
            }
        }
        if (student != null) {
            System.out.println("Closest lesson for student " + student.getName() + " " + student.getSurname() + " " + student.getStudent_id());
            System.out.println(student.getLessons().stream().sorted(Comparator.comparing(Lesson::getTime)).toList().get(0));
        } else {
            System.out.println("Couldn't find student in db, please try again.");
        }
    }
}
