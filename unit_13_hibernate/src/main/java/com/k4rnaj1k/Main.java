package com.k4rnaj1k;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Configuration configuration = new Configuration().configure();
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            try {
                DBWorker dbWorker = new DBWorker(session);
                System.out.println("Initialize database?(y/n)");
                if (s.nextLine().toLowerCase().startsWith("y")) {
                    session.beginTransaction();
                    System.out.println("Initializing db.");
                    dbWorker.init(session);
                    session.getTransaction().commit();
                }
                dbWorker.printStudents();
                System.out.println("Please input the name and the surname of the needed student.");
                String name = s.nextLine();
                String surname = s.nextLine();
                dbWorker.getStudent(name, surname);
            } catch (HibernateException e) {
                session.getTransaction().rollback();
            } finally {
                session.close();
            }
        }
    }
}
