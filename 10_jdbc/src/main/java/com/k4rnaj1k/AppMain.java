package com.k4rnaj1k;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class AppMain {
    public void start() {
        Properties properties = loadProps();
        if (!properties.containsKey("url")) {
            System.out.println("Couldn't find url properties, stopping the app's execution.");
            throw new RuntimeException();
        }
        String url = properties.getProperty("url");
        System.out.println("Opening connection with the db.");
        try (Connection conn = DriverManager.getConnection(url, properties)) {
            DBWorker dbWorker = new DBWorker(conn);
            HashMap<Integer, Integer> answers = solve(dbWorker);
            dbWorker.insertAnsw(answers);
            System.out.println("Successfully solved all the problems, shutting down.");
        } catch (SQLException e) {
            System.out.println("Couldn't connect to db.");
        }
    }

    private HashMap<Integer, Integer> solve(DBWorker db) {
        LinkedHashMap<Integer, Map.Entry<Integer, Integer>> problems = db.getProblems();
        DijkstraAlg alg = new DijkstraAlg();
        int[][] matrix = db.getRoutes();
        int citiescount = db.getCitiesCount();
        HashMap<Integer, Integer> answers = alg.solve(problems, matrix, citiescount);
        return answers;
    }

    private Properties loadProps() {
        Properties properties = new Properties();
        try {
            properties.load(DijkstraAlg.class.getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return properties;
    }
}
