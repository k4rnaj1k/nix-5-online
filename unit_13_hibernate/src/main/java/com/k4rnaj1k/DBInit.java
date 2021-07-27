package com.k4rnaj1k;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBInit {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(DBInit.class);
        Configuration configuration = new Configuration().configure();
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            try {
                DBWorker dbWorker = new DBWorker(() -> session);
                session.beginTransaction();
                logger.info("Initializing the database with some default values.");
                System.out.println("Initializing db.");
                dbWorker.init();
                logger.info("Successfully initialized the db.");
                session.getTransaction().commit();
                dbWorker.printStudents();
            } catch (HibernateException e) {
                logger.error("An exception happened during the runtime, stopping the app and rolling back.");
                logger.error("Exception message: " + e.getMessage());
                session.getTransaction().rollback();
            } finally {
                session.close();
            }
        }
    }
}
