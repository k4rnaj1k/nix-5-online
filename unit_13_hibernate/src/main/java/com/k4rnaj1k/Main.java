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
                System.out.println("Initialize database values?(y/n)");
                if (s.nextLine().toLowerCase().startsWith("y")) {
                    session.beginTransaction();
                    logger.info("Initializing the database with some default values.");
                    System.out.println("Initializing db.");
                    dbWorker.init();
                    logger.info("Successfully initialized the db.");
                    session.getTransaction().commit();
                }
                dbWorker.printStudents();
                System.out.println("Please input the name of the needed student.");
                String name = s.nextLine();
                System.out.println("Now enter the surname.");
                String surname = s.nextLine();
                logger.warn("Searching for student " + name + " " + surname + ".");
                dbWorker.getStudent(name, surname);
                logger.info("Successfully found the given student.");
            } catch (HibernateException e) {
                logger.error("An exception happenned during the runtime, stopping the app and rolling back.");
                logger.error("Exception message: " + e.getMessage());
                session.getTransaction().rollback();
            } finally {
                session.close();
            }
        }
    }
}
