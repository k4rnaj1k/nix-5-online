package com.k4rnaj1k;

import com.k4rnaj1k.task1.Task1;
import com.k4rnaj1k.task2.Task2;

import java.util.List;
import java.util.Scanner;

public class ThreadsMain {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Task task;
        while (true) {
            System.out.println("""
                    What would you like to do?
                    1 - launch task 1(50 hello threads);
                    2 - launch task 2(one thread checks if the numbers are simple, the other one counts their amount).
                    Anything else to exit.""");
            switch (s.nextLine()) {
                case "1" -> task = new Task1();
                case "2" -> task = new Task2(List.of(1, 2, 3, 4, 5, 6, 7, 100, 17));
                default -> {
                    return;
                }
            }
            task.execute();
        }

    }
}
