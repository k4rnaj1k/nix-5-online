package com.k4rnaj1k;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class CSVOutput {

    private Logger loggerInfo = LoggerFactory.getLogger("info");
    private Logger loggerWarn = LoggerFactory.getLogger("warn");
    private Logger loggerError = LoggerFactory.getLogger("error");

    public void start(String email, String username, String password) {
        loggerWarn.warn("String the process of reading properties from app.properties.");
        Properties props = loadProps();
        loggerInfo.info("Successfully read connection's properties from file.");
        try (Connection conn = DriverManager.getConnection(props.getProperty("url"), props)) {
            ResultSet userdata;
            Long user_id;
            loggerWarn.warn("Searching for current user (" + username + ") in db.");
            try (PreparedStatement preparedStatement = conn.prepareStatement("select * from users where email =? and username=? and password=?")) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, password);

                userdata = preparedStatement.executeQuery();
                if (!userdata.next()) {
                    System.out.println("Couldn't find the user in db, stopping the execution.");
                    loggerError.error("Couldn't find the user " + username + " in db.");
                    System.exit(1);
                }
                user_id = userdata.getLong("user_id");
                loggerInfo.info("Successfully found the user in the database.");
            }
            String input;
            Scanner s = new Scanner(System.in);
            loggerWarn.warn("Retrieving the accounts from the database that belong to the current user.");
            try (PreparedStatement statement = conn.prepareStatement("select * from accounts where user_id = ? order by account_id asc")) {
                statement.setLong(1, user_id);
                ResultSet accounts = statement.executeQuery();
                Long account_quantity = 1L;
                while (accounts.next()) {
                    System.out.println(accounts.getLong("account_id") + " " + accounts.getString("name"));
                    account_quantity++;
                }
                System.out.println("Please choose the account you'd like to get the statement of.\nOr input * to get the statements of all the accounts.");
                writeToFile(conn, s, account_quantity);
            }
        } catch (SQLException e) {
            System.out.println("There was an exception during the process of connection to the database.");
        }
    }

    private Properties loadProps() {
        Properties res = new Properties();
        try (InputStream stream = CSVOutput.class.getClassLoader().getResourceAsStream("app.properties")) {
            res.load(stream);
        } catch (IOException e) {
            System.out.println("Couldn't load properties from app.properties.");
            throw new RuntimeException();
        }
        return res;
    }

    private void writeToFile(Connection conn, Scanner s, Long accounts) {
        loggerWarn.warn("Starting the process of writing the data to the file.");
        String sql = """
                select o.operation_id, o.time, o.sum, ac.name, oc.type, oc.type_name from operations o
                join accounts ac on ac.account_id=o.account_id
                join operation_categories oc on oc.id=o.category_id
                """;
        String input;
        boolean flag = true;
        while (flag) {
            input = s.nextLine();
            try {
                if (!input.equals("*")) {
                    long account_id = Long.parseLong(input);
                    if (account_id < accounts) {
                        sql = sql.concat("where ac.account_id=" + account_id + "\n");
                    } else {
                        loggerInfo.info("User has entered unreal account id.");
                        throw new NumberFormatException();
                    }
                }
                flag = false;
            } catch (NumberFormatException n) {
                System.out.println("Wrong input, try again.");
            }
        }
        System.out.println("How would you like the operations to get sorted by time?\n" +
                "1 - ascending\n" +
                "2 - descending\n");
        sql = sql.concat("order by o.time");
        if (s.nextLine().equals("1")) {
            sql = sql.concat(" asc");
            loggerInfo.info("User has decided to sort the output ascending.");
        } else {
            sql = sql.concat(" desc");
            loggerInfo.info("User has decided to sort the output descending.");
        }
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            System.out.println("Please input the file name");
            String fileName = s.nextLine();
            if(!fileName.endsWith(".csv"))
                fileName = fileName.concat(".csv");
            loggerInfo.info("Output file name = " + fileName);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
                writer.write("operation id,time,sum,type,category\n");
                ResultSet set = statement.executeQuery();
                while (set.next()) {
                    String date = set.getTimestamp("time").toLocalDateTime().toString();
                    writer.write(
                            set.getString("operation_id") + "," +
                                    date + "," +
                                    set.getLong("sum") + "," +
                                    set.getString("type") + "," +
                                    set.getString("type_name"));
                    writer.write("\n");
                }
            }
            loggerInfo.info("Successfully output the data to file.");
        } catch (SQLException | IOException e) {
            System.out.println("Sorry, an unexpected error has occurred.");
            loggerError.error("An unexpected error has occurred. Message: " + e.getMessage());
        }
    }
}
