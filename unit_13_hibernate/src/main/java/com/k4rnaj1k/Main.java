package com.k4rnaj1k;

import com.k4rnaj1k.entity.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Comparator;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().configure();
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            try {
                session.beginTransaction();
                Course course = new Course();
                course.setCourseName("java");
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
                lesson.setGroup(group);
                lesson.setTime(new Date());
                session.persist(lesson);


                Theme theme = new Theme();
                theme.setName("jpa/jdbc");
                lesson.setTheme(theme);
                session.persist(theme);

                Grade grade = new Grade();
                grade.setLesson(lesson);
                grade.setStudent(student);
                grade.setGrade(((byte)10));
                session.persist(grade);


                for (int i = 0; i < 10; i++) {
                    System.out.print("---");
                }
                System.out.println("\n");
                Lesson closestLesson = student.getLessons().stream().sorted(Comparator.comparing(Lesson::getTime)).toList().get(0);
                System.out.println(closestLesson);
                for (int i = 0; i < 10; i++) {
                    System.out.print("---");
                }
                System.out.println("\n");
                session.getTransaction().commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
            }
        }
    }
}
