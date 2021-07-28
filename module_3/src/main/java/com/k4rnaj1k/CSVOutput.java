package com.k4rnaj1k;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class CSVOutput {
    public static void main(String[] args) {
        Properties props = loadProps();
        String email = System.getenv("email");
        String username = System.getenv("username");
        String password = System.getenv("password");
        try (Connection conn = DriverManager.getConnection(props.getProperty("url"), props)) {
            ResultSet userdata;
            try (PreparedStatement preparedStatement = conn.prepareStatement("select * from users where email =? and username=? and password=?")) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, password);

                userdata = preparedStatement.executeQuery();
                if (!userdata.next()) {
                    System.out.println("Couldn't find the user in db, stopping the execution.");
                    System.exit(1);
                }

                String input;
                try (PreparedStatement statement = conn.prepareStatement("select * from accounts where user_id = ? order by account_id asc")) {
                    statement.setInt(1, userdata.getInt("user_id"));
                    ResultSet accounts = statement.executeQuery();
                    while (accounts.next()) {
                        System.out.println(accounts.getInt("account_id") + " " + accounts.getString("name"));
                    }

                    Scanner s = new Scanner(System.in);
                    System.out.println("Please choose the account you'd like to get the statement of.\nOr input * to get the statements of all the accounts.");

                    input = s.nextLine();
                }
                writeToFile(conn, input);


            }

        } catch (SQLException e) {
            System.out.println("There was an exception during the process of connection to the database.");
        }
    }

    private static Properties loadProps() {
        Properties res = new Properties();
        try (InputStream stream = CSVOutput.class.getClassLoader().getResourceAsStream("app.properties")) {
            res.load(stream);
        } catch (IOException e) {
            System.out.println("Couldn't load properties from app.properties.");
            throw new RuntimeException();
        }
        return res;
    }

    private static void writeToFile(Connection conn, String input) {
        String sql = """
                select o.operation_id, o.time, o.sum, ac.name, oc.type, oc.type_name from operations o
                join accounts ac on ac.account_id=o.account_id
                join operation_categories oc on oc.id=o.category_id
                """;
        if (!input.equals("*")) {
            long account_id = Long.parseLong(input);
            sql = sql.concat("where ac.account_id=" + account_id + "\n");
        }
        sql = sql.concat("order by o.time desc");
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("statement.csv"))) {
                writer.write("operation id,time,sum,type,category\n");
                ResultSet set = statement.executeQuery();
                while (set.next()) {
                    String date = set.getTimestamp("time").toLocalDateTime().toString();
                    writer.write(set.getString("operation_id") + "," +
                            date + "," +
                            set.getLong("sum") + "," +
                            set.getString("type") + "," +
                            set.getString("type_name"));
                    writer.write("\n");
                }
            }
        } catch (SQLException | IOException e) {
            System.out.println("Couldn't write to file.");
        }
    }
}
