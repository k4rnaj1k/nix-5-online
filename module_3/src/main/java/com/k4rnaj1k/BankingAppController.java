package com.k4rnaj1k;

import com.k4rnaj1k.model.Account;
import com.k4rnaj1k.model.Operation;
import com.k4rnaj1k.model.OperationCategory;
import com.k4rnaj1k.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class BankingAppController {
    Logger loggerInfo = LoggerFactory.getLogger("info");
    Logger loggerWarn = LoggerFactory.getLogger("warn");
    Logger loggerError = LoggerFactory.getLogger("error");

    public void start(String email, String username, String password) {
        Configuration configuration = new Configuration().configure();
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.openSession();

            Query<User> query = session.createQuery("from User where email=:email and username=:username and password=:password");
            query.setParameter("email", email)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .setMaxResults(1);
            User currentUser = query.uniqueResult();
            if (currentUser != null) {
                loggerInfo.info("Successfully logged in.");
                System.out.println("Logged in as " + username);
            } else {
                loggerError.error("Couldn't log in, stopping the app's execution.");
                System.exit(1);
            }

            Scanner s = new Scanner(System.in);
            boolean flag = true;
            while (flag) {
                System.out.println("Would you like to add a new operation?(y/n)");
                if (s.nextLine().toLowerCase().startsWith("y")) {
                    loggerInfo.info("Starting the process of operation addition.");
                    addNewOperation(s, currentUser, session);
                } else {
                    flag = false;
                }
            }
        }
    }

    private void addNewOperation(Scanner s, User user, Session session) {
        try {
            System.out.println("Choose the account to add the operation to.");
            List<Account> userAccounts = user.getAccounts();
            for (int i = 1; i <= userAccounts.size(); i++) {
                System.out.println(i + " |" + userAccounts.get(i - 1).getName() + "| balance " + userAccounts.get(i - 1).getBalance());
            }
            int chosenAccountIndex = Integer.parseInt(s.nextLine()) - 1;
            Account account = userAccounts.get(chosenAccountIndex);
            loggerInfo.info("User has decided to add the operation to account " + account.getName());

            System.out.println("""
                    Type of the new operation?
                    1 - income
                    2 - expense""");
            boolean flag = true;
            var type = OperationCategory.Type.EXPENCE;
            while (flag) {
                try {
                    int choice = Integer.parseInt(s.nextLine());
                    type = OperationCategory.Type.values()[choice - 1];
                    flag = false;
                } catch (NumberFormatException e) {
                    System.out.println("Wrong input, try again.");
                }
            }
            loggerInfo.info("User has decided to add an operation with " + type.name() + " type.");

            Query<OperationCategory> q = session.createQuery("from OperationCategory where type=:type");
            q.setParameter("type", type);
            List<OperationCategory> categories = q.getResultList();

            for (int i = 1; i <= categories.size(); i++) {
                System.out.println(i + " " + categories.get(i - 1).getType_name());
            }
            int chosenCategory = 1;
            flag = true;
            while (flag) {
                try {
                    chosenCategory = Integer.parseInt(s.nextLine()) - 1;
                    if (chosenCategory > categories.size()) {
                        throw new NumberFormatException();
                    }
                    flag = false;
                } catch (NumberFormatException e) {
                    System.out.println("Wrong input, try again.");
                }
            }
            System.out.println("Adding operation with category " + categories.get(chosenCategory).getType_name());
            loggerInfo.info("Adding operation with category " + categories.get(chosenCategory).getType_name());
            System.out.println("Please input the sum.");

            session.getTransaction().begin();
            Long sum = Long.parseLong(s.nextLine());

            loggerWarn.warn("Starting the process of operation addition.");

            Operation operation = new Operation(categories.get(chosenCategory), sum, account);
            session.persist(operation);
            session.getTransaction().commit();

            loggerInfo.info("Successfully committed changes to the database.");

            System.out.println("Successfully committed the transaction to the db.");
            System.out.println("New balance: " + account.getBalance());

        } catch (Exception e) {
            System.out.println("There was an error during the operation's addition, aborting.");
            session.getTransaction().rollback();
            loggerError.error("There was an exception during the operation's addition, rolling back the transaction.");
        }
    }
}
