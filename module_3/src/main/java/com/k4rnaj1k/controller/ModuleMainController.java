package com.k4rnaj1k.controller;

import com.k4rnaj1k.controller.BankingAppController;
import com.k4rnaj1k.controller.CSVOutput;
import com.k4rnaj1k.service.DBInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class ModuleMainController {
    private String email, username, password;
    private Logger loggerInfo = LoggerFactory.getLogger("info");
    private Logger loggerWarn = LoggerFactory.getLogger("warn");
    private Logger loggerError = LoggerFactory.getLogger("error");

    public void start() {
        loggerInfo.info("Starting app.");

        loggerInfo.info("Initializing login fields.");
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
        loggerWarn.warn("Reading user's credentials from environment variables.");
        email = System.getenv("email");
        username = System.getenv("username");
        password = System.getenv("password");
        if (email == null || username == null || password == null) {
            loggerError.error("Couldn't initialize login fields, please make sure you've set up all the necessary environment variables(email, username, password).");
            throw new RuntimeException();
        }
    }

    private void bankLogIn() {
        BankingAppController app = new BankingAppController();
        loggerInfo.info("The user has decided to log in into his bank account.");
        app.start(email, username, password);
    }

    private void dbInit() {
        System.out.println("Initializing the database...");
        DBInit.init(email, username, password);
        System.out.println("Successfully initialized. Created a user with a username " + username + " 2 accounts, few operations and a few operation categories.");
        loggerInfo.info("Initialized a user with username " + username);
    }

    private void dataExport(){
        CSVOutput output = new CSVOutput();
        loggerWarn.warn("Starting the process of data export.");
        output.start(email, username, password);
        System.out.println("Successfully exported the data to file.");
    }
}
