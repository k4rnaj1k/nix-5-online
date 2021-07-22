package com.k4rnaj1k;

import java.sql.*;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DBWorker {
    private Connection conn;

    public DBWorker(Connection conn) {
        this.conn = conn;
    }

    public void insertAnsw(HashMap<Integer, Integer> answers){
        try(PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO solutions(problem_id, cost) VALUES (?, ?)")){
            for (Integer id :
                    answers.keySet()) {
                preparedStatement.setInt(1, id);
                int cost = answers.get(id);
                preparedStatement.setInt(2, cost);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }catch (SQLException e){
            System.out.println("Couldn't insert answers into solutions table.");
        }
    }

    public int[][] getRoutes() {
        int citiescount = getCitiesCount();
        int[][] matrix = new int[citiescount][citiescount];
        try(PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM routes")){
            ResultSet set = preparedStatement.executeQuery();
            while(set.next()){
                matrix[set.getInt("from_id")-1][set.getInt("to_id")-1] = set.getInt("cost");
            }
        }catch (SQLException e){
            System.out.println("Couldn't parse the routes from db, quitting.");
        }
        return matrix;
    }

    public int getCitiesCount() {
        try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(*) FROM locations")) {
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()) {
                return set.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Couldn't get the cities count from db.");
        }
        throw new RuntimeException();
    }

    public LinkedHashMap<Integer, Map.Entry<Integer, Integer>> getProblems(){
        LinkedHashMap<Integer, Map.Entry<Integer, Integer>> problems = new LinkedHashMap<>();
        try(PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM problems LEFT JOIN solutions ON public.problems.id=public.solutions.problem_id WHERE cost IS NULL;")){
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()){
                int id = set.getInt("id");
                int from_id = set.getInt("from_id");
                int to_id = set.getInt("to_id");
                problems.put(id, new AbstractMap.SimpleEntry<>(from_id, to_id));
            }
            if(problems.size()==0){
                System.out.println("No problems to solve, shutting down.");
                throw new RuntimeException();
            }
            return problems;
        }catch (SQLException e){
            System.out.println("Couldn't load problems from db.");
            throw new RuntimeException();
        }
    }
}
