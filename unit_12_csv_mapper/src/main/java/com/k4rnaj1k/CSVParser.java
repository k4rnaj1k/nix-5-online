package com.k4rnaj1k;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVParser {

    String file;

    public CSVParser(String file) {
        this.file = file;
    }

    public String get(int row, String header) {
        return contents.get(row - 1)[headers.get(header)];
    }

    public int getSize() {
        return contents.size();
    }

    public String get(int row, int cell) {
        return contents.get(row - 1)[cell];
    }

    private Map<String, Integer> headers;
    private List<String[]> contents;

    public void parse() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            headers = new HashMap<>();
            String[] headersArray = reader.readLine().split(",");
            for (int i = 0; i < headersArray.length; i++) {
                headers.put(headersArray[i], i);
            }
            contents = new ArrayList<>();
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                contents.add(nextLine.split(","));
            }
        } catch (IOException e) {
            System.out.println("Couldn't parse the file, ");
        }
    }
}
