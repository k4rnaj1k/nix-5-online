package com.k4rnaj1k.controller;

import com.k4rnaj1k.service.CSVOutputService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.*;

public class CSVOutputController {

    private final String email, username, password;
    private CSVOutputService service;

    public CSVOutputController(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    private static final Logger logger = LoggerFactory.getLogger("log");
    private final Long ALL_ACCOUNTS = -1L;

    public void startOutput() {
        logger.warn("String the process of reading properties from app.properties.");
        Properties props = loadProps();
        logger.info("Successfully read connection's properties from file.");
        try (Connection conn = DriverManager.getConnection(props.getProperty("url"), props)) {
            service = new CSVOutputService(email, username, password);
            service.setConnection(conn);
            logger.warn("Searching for current user (" + username + ") in db.");
            long userId = service.searchForUser(conn);
            logger.warn("Retrieving the accounts from the database that belong to the current user.");
            Map<Long, Map.Entry<String, Long>> accounts = service.getAccounts(userId);
            writeToFile(conn, accounts);
            System.out.println("Successfully exported the data to file.");
        } catch (SQLException e) {
            System.out.println("There was an exception during the process of connection to the database.");
            System.out.println(e.getMessage());
        }
    }

    private Properties loadProps() {
        Properties res = new Properties();
        try (InputStream stream = CSVOutputController.class.getClassLoader().getResourceAsStream("app.properties")) {
            res.load(stream);
            res.setProperty("user", System.getenv("db_user"));
            res.setProperty("password", System.getenv("db_pass"));
        } catch (IOException e) {
            System.out.println("Couldn't load properties from app.properties.");
            throw new RuntimeException();
        }
        return res;
    }

    private long chooseAccount(Scanner s, Map<Long, Map.Entry<String, Long>> accounts) {
        Set<Long> accountIds = accounts.keySet();
        NumberFormat n = NumberFormat.getCurrencyInstance(new Locale("uk", "UA"));
        System.out.println("Please choose the account you'd like to get the statement of.\nOr input * to get the statements of all the accounts.");
        for (Long accountId :
                accountIds) {
            String accountName = accounts.get(accountId).getKey();
            Long accountBalance = accounts.get(accountId).getValue();
            System.out.println(accountId + " |" + accountName + "\t|balance| " + n.format(accountBalance / 100.0));
        }
        String input;
        while (true) {
            try {
                input = s.nextLine();
                if (input.equals("*")) return ALL_ACCOUNTS;
                long chosenAccountId = Long.parseLong(input);
                if (accounts.containsKey(chosenAccountId))
                    return chosenAccountId;
                else throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println("Wrong input, try again.");
            }
        }
    }

    private void writeToFile(Connection conn, Map<Long, Map.Entry<String, Long>> accounts) {
        Scanner s = new Scanner(System.in);
        logger.warn("Starting the process of writing the data to the file.");
        long accountId = chooseAccount(s, accounts);

        System.out.println("""
                How would you like the operations to get sorted by time?
                1 - ascending
                2 - descending""");
        boolean asc;
        if (s.nextLine().equals("1")) {
            asc = true;
            logger.info("User has decided to sort the output ascending.");
        } else {
            asc = false;
            logger.info("User has decided to sort the output descending.");
        }
        System.out.println("Please input the file name");
        String fileName = s.nextLine();
        try {
            if (!fileName.endsWith(".csv"))
                fileName = fileName.concat(".csv");
            logger.info("Output file name = " + fileName);
            service.writeToFile(fileName, asc, accountId);
            logger.info("Successfully output the data to file.");
        } catch (SQLException | IOException e) {
            System.out.println("Sorry, an unexpected error has occurred.");
            logger.error("An unexpected error has occurred. Message: " + e.getMessage());
        }
    }
}
