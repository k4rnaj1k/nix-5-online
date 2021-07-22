package com.k4rnaj1k;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DijkstraAlg {

    public HashMap<Integer, Integer> solve(LinkedHashMap<Integer, Map.Entry<Integer, Integer>> problems, int[][] matrix, int cities) {
        HashMap<Integer, Integer> answers = new HashMap<>();
        for (Integer id :
                problems.keySet()) {
            int from_id = problems.get(id).getKey()-1;
            int to_id = problems.get(id).getValue()-1;
            int cost = findPath(from_id, to_id, matrix, cities);
            answers.put(id, cost);
        }
        return answers;
    }

    private int findPath(int start, int end, int[][] matrix, int citiescount) {
        int temp;
        int[] v = new int[matrix[0].length];
        int[] d = new int[matrix[0].length];
        int minindex, min;
        Arrays.fill(v, 1);
        Arrays.fill(d, 10000);
        d[start] = 0;
        do {
            minindex = 30000;
            min = 10000;
            for (int i = 0; i < citiescount; i++) {
                if ((v[i] == 1) && (d[i] < min)) {
                    min = d[i];
                    minindex = i;
                }
            }
            if (minindex != 30000) {
                for (int i = 0; i < citiescount; i++) {
                    if (matrix[minindex][i] != 0)
                        if (matrix[minindex][i] > 0) {
                            temp = min + matrix[minindex][i];
                            if (temp < d[i]) {
                                d[i] = temp;
                            }
                        }
                }
                v[minindex] = 0;
            }
        } while (minindex < 30000);
        return d[end];
    }
}
