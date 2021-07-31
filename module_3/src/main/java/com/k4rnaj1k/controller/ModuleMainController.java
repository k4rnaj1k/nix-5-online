package com.k4rnaj1k.controller;

import com.k4rnaj1k.service.DBInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class ModuleMainController {
    private String email, username, password;
    private static final Logger logger = LoggerFactory.getLogger("log");

    public void start() {
        logger.info("Starting app.");

        logger.info("Initializing login fields.");
        fieldInit();

        Scanner s = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            System.out.println("""
                    What would you like to do?
                    1 - initialize the database values;
                    2 - log into the banking app;
                    3 - export the accounts data;
                    Anything else to quit.""");

            switch (s.nextLine()) {
                case "1" -> dbInit();
                case "2" -> bankLogIn();
                case "3" -> dataExport();
                default -> flag = false;
            }
        }
    }

    private void fieldInit() {
        logger.warn("Reading user's credentials from environment variables.");
        email = System.getenv("email");
        username = System.getenv("username");
        password = System.getenv("password");
        if (email == null || username == null || password == null) {
            logger.error("Couldn't initialize login fields, please make sure you've set up all the necessary environment variables(email, username, password).");
            throw new RuntimeException();
        }
    }

    private void bankLogIn() {
        BankingAppController app = new BankingAppController();
        logger.info("The user has decided to log in into his bank account.");
        app.start(email, username, password);
    }

    private void dbInit() {
        System.out.println("Initializing the database...");
        DBInit.init(email, username, password);
        System.out.println("Successfully initialized. Created a user with a username " + username + " 2 accounts, few operations and a few operation categories.");
        logger.info("Initialized a user with username " + username);
    }

    private void dataExport() {
        CSVOutputController output = new CSVOutputController(email, username, password);
        logger.warn("Starting the process of data export.");
        output.startOutput();
    }
}
