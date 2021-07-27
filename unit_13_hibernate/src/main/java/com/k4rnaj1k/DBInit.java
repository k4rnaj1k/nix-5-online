package com.k4rnaj1k;

import com.k4rnaj1k.model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DBInit {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(DBInit.class);
        Configuration configuration = new Configuration().configure();
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            try {
                session.beginTransaction();
                logger.info("Initializing the database with some default values.");
                System.out.println("Initializing db.");
                init(session);
                logger.info("Successfully initialized the db.");
                session.getTransaction().commit();
            } catch (HibernateException e) {
                logger.error("An exception happened during the runtime, stopping the app and rolling back.");
                logger.error("Exception message: " + e.getMessage());
                session.getTransaction().rollback();
            } finally {
                session.close();
            }
        }
    }

    private static void init(Session session) {
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
}
