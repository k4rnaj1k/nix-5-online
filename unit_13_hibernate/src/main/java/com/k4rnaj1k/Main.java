package com.k4rnaj1k;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Main.class);
        Scanner s = new Scanner(System.in);
        Configuration configuration = new Configuration().configure();
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            try {
                DBWorker dbWorker = new DBWorker(() -> session);
                dbWorker.printStudents();
                System.out.println("Please input the id of the needed student.");
                Long id = Long.parseLong(s.nextLine());
                logger.warn("Searching for student by id " + id + ".");
                dbWorker.getStudentsClosestLesson(id);
            } catch (HibernateException e) {
                logger.error("An exception happenned during the runtime, stopping the app and rolling back.");
                logger.error("Exception message: " + e.getMessage());
            } finally {
                session.close();
            }
        }
    }
}
