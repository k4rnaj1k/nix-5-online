package com.k4rnaj1k;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ModuleMain {
    public static void main(String[] args) {
        Logger loggerInfo = LoggerFactory.getLogger("info");

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.systemDefault());

        loggerInfo.warn("Starting app " + formatter.format(Instant.now()));

        BankingAppController controller = new BankingAppController();
        String email = System.getenv("email");
        String username = System.getenv("username");
        String password = System.getenv("password");
        if (email == null || username == null || password == null) {
            System.out.println("Couldn't find environment variables needed for login. Please make sure you've set them up (email, username, password).");
            System.exit(0);
        }
        loggerInfo.info("Trying to log in " + username);
        controller.start(email, username, password);
    }
}
