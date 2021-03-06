package com.k4rnaj1k.service;

import com.k4rnaj1k.model.Account;
import com.k4rnaj1k.model.Operation;
import com.k4rnaj1k.model.OperationCategory;
import com.k4rnaj1k.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.LoggerFactory;

public class DBInit {
    public static void init(String email, String username, String password) {
        Configuration configuration = new Configuration().configure();
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.openSession();

            session.getTransaction().begin();

            //Creating a few basic operation categories.
            var scholarship = new OperationCategory(OperationCategory.Type.INCOME, "Scholarship");
            var salary = new OperationCategory(OperationCategory.Type.INCOME, "Salary");
            var internetPayment = new OperationCategory(OperationCategory.Type.EXPENCE, "Internet payment");
            var videoGamesExpense = new OperationCategory(OperationCategory.Type.EXPENCE, "Video Games Expense");
            session.persist(scholarship);
            session.persist(salary);
            session.persist(internetPayment);
            session.persist(videoGamesExpense);

            //Creating user and 2 of his accounts.
            var user = new User(email, username, password);
            var account = new Account(100_00L, "main account", user);
            var internetAccount = new Account(100_00L, "internet expenses account", user);
            session.persist(user);
            session.persist(account);
            session.persist(internetAccount);


            //Creating 2 operations for each account.

            var operation = new Operation(scholarship, 1200_00L, account);
            var operation1 = new Operation(internetPayment, 140_00L, account);
            session.persist(operation);
            session.persist(operation1);

            var steamPayment = new Operation(videoGamesExpense, 100_00L, internetAccount);
            var salaryOperation = new Operation(salary, 1000_00L, internetAccount);
            session.persist(steamPayment);
            session.persist(salaryOperation);

            session.getTransaction().commit();

            session.close();
        } catch (Exception e) {
            System.out.println("There was an exception during the database initialization.");
            LoggerFactory.getLogger("error").error("There was an exception during the database initialization. " + e.getMessage());
        }
    }
}
