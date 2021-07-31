package com.k4rnaj1k.controller;

import com.k4rnaj1k.model.Account;
import com.k4rnaj1k.model.OperationCategory;
import com.k4rnaj1k.model.User;
import com.k4rnaj1k.service.BankingAppService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class BankingAppController {
    private static final Logger logger = LoggerFactory.getLogger("log");
    private BankingAppService service;


    public void start(String email, String username, String password) {
        Configuration configuration = new Configuration().configure();
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            service = new BankingAppService(session);
            User currentUser = service.getUser(email, username, password);

            if (currentUser != null) {
                logger.info("Successfully logged in.");
                System.out.println("Logged in as " + username);
            } else {
                logger.error("Couldn't log in, stopping the app's execution.");
                System.exit(1);
            }

            Scanner s = new Scanner(System.in);
            boolean flag = true;
            System.out.println("""
                    What would you like to do?
                    1 - get the list of all the accounts;
                    2 - add the operation to the account;
                    Anything else to return to the previous menu.""");
            while (flag) {
                System.out.println("What would you like to do next?");
                switch (s.nextLine()) {
                    case "1" -> printAccounts(currentUser);
                    case "2" -> addNewOperation(s, currentUser);
                    default -> flag = false;
                }
            }
        }
    }

    private void addNewOperation(Scanner s, User user) {
        try {
            System.out.println("Choose the account to add the operation to.");

            Account account = chooseAccount(user, s);
            logger.info("User has decided to add the operation to account " + account.getName());

            System.out.println("""
                    Type of the new operation?
                    1 - income
                    2 - expense""");
            var type = chooseType(s);

            logger.info("User has decided to add an operation with " + type.name() + " type.");

            OperationCategory chosenCategory = chooseCategory(s, type);

            System.out.println("Adding operation with category " + chosenCategory.getCategoryName());
            logger.info("Adding operation with category " + chosenCategory.getCategoryName());
            System.out.println("Please input the sum.");
            Long sum = Long.parseLong(s.nextLine().replace(".", "")) * 100;
            logger.warn("Starting the process of operation addition.");

            service.addOperation(chosenCategory, sum, account);

            logger.info("Successfully committed changes to the database.");

            System.out.println("Successfully committed the transaction to the db.");
            System.out.println("New balance: " + formatBalance(account.getBalance()));
        } catch (Exception e) {
            System.out.println("There was an error during the operation's addition, aborting.");
            logger.error("There was an exception during the operation's addition, rolling back the transaction.");
        }
    }

    private void printAccounts(User user) {
        List<Account> userAccounts = user.getAccounts();
        for (int i = 1; i <= userAccounts.size(); i++) {
            long balance = userAccounts.get(i - 1).getBalance();
            System.out.println(i + " |" + userAccounts.get(i - 1).getName() + "\t|balance| " + formatBalance(balance));
        }
    }

    private static final NumberFormat n = NumberFormat.getCurrencyInstance(new Locale("uk", "UA"));

    private String formatBalance(long balance) {
        return n.format(balance / 100.0);
    }

    private Account chooseAccount(User user, Scanner s) {
        List<Account> userAccounts = user.getAccounts();
        int chosenAccountIndex;
        while (true) {
            try {
                chosenAccountIndex = Integer.parseInt(s.nextLine()) - 1;
                return userAccounts.get(chosenAccountIndex);
            } catch (NumberFormatException e) {
                System.out.println();
            }
        }

    }

    private OperationCategory.Type chooseType(Scanner s) {
        OperationCategory.Type type;
        while (true) {
            try {
                int choice = Integer.parseInt(s.nextLine()) - 1;
                return OperationCategory.Type.values()[choice];
            } catch (NumberFormatException e) {
                System.out.println("Wrong input, try again.");
            }
        }
    }

    private OperationCategory chooseCategory(Scanner s, OperationCategory.Type type) {
        List<OperationCategory> categories = service.getOperationCategories(type);
        System.out.println("Choose the operation's category.");
        for (int i = 1; i <= categories.size(); i++) {
            System.out.println(i + " " + categories.get(i - 1).getCategoryName());
        }
        int chosenCategory;
        while (true) {
            try {
                chosenCategory = Integer.parseInt(s.nextLine()) - 1;
                return categories.get(chosenCategory);
            } catch (NumberFormatException e) {
                System.out.println("Wrong input, try again.");
            }
        }
    }
}
