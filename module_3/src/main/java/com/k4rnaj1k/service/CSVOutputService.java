package com.k4rnaj1k.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class CSVOutputService {
    private Connection conn;

    private final String username, email, password;

    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    public CSVOutputService(String email, String username, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    private static final Logger logger = LoggerFactory.getLogger("log");

    public Long searchForUser(Connection conn) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement("select * from users where email =? and username=? and password=?")) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);

            ResultSet userdata = preparedStatement.executeQuery();
            if (!userdata.next()) {
                System.out.println("Couldn't find the user in db, stopping the execution.");
                logger.error("Couldn't find the user {} in db.", username);
                System.exit(1);
            }
            Long userId = userdata.getLong("id");
            logger.info("Successfully found the user in the database.");
            return userId;
        }
    }

    public Map<Long, Map.Entry<String, Long>> getAccounts(Long userId) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement("select * from accounts where user_id = ? order by id asc")) {
            statement.setLong(1, userId);
            ResultSet accountsSet = statement.executeQuery();
            HashMap<Long, Map.Entry<String, Long>> accounts = new HashMap<>();
            while (accountsSet.next()) {
                long id = accountsSet.getLong("id");
                String name = accountsSet.getString("name");
                Long balance = accountsSet.getLong("balance");
                Map.Entry<String, Long> entry = new AbstractMap.SimpleEntry<>(name, balance);
                accounts.put(id, entry);
            }
            return accounts;
        }
    }

    public void writeToFile(String fileName, boolean asc, Long accountId) throws SQLException, IOException {
        String sql = """
                select o.id, o.time, o.sum, ac.name, oc.type, oc.category_name from operations o
                join accounts ac on ac.id=o.account_id
                join operation_categories oc on oc.id=o.category_id
                """;
        if (!accountId.equals(-1L)) {//-1 = all acounts
            sql = sql.concat("where o.account_id=" + accountId);
        }
        if (asc) {
            sql = sql.concat("order by o.time asc");
        } else {
            sql = sql.concat("order by o.time desc");
        }
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
                writer.write("operation id,time,sum,type,category\n");
                ResultSet set = statement.executeQuery();
                while (set.next()) {
                    String date = set.getTimestamp("time").toLocalDateTime().toString();
                    writer.write(
                            set.getString("id") + "," +
                                    date + "," +
                                    set.getLong("sum") + "," +
                                    set.getString("type") + "," +
                                    set.getString("category_name"));
                    writer.write("\n");
                }
            }
        }
    }
}
