package com.k4rnaj1k;

import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Hippodrome {
    public void start() {
        Scanner s = new Scanner(System.in);
        System.out.print("Input your horses number(1-10):\n>");
        Integer choice = Integer.parseInt(s.nextLine());
        System.out.println("3...2...1...GO");
        CopyOnWriteArrayList<Integer> winners = new CopyOnWriteArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 1; i <= 10; i++) {
            service.execute(new Horse(winners, i));
        }
        service.shutdown();
        System.out.println("The race has started.");
        boolean finished = false;
        while (!finished) {
            try {
                finished = service.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                System.out.println("An unexpected error has occurred. " + e.getMessage());
            }
        }
        if (Objects.equals(winners.get(0), choice)) {
            System.out.println("You have won.");
        } else {
            System.out.println("You have lost.");
        }
        System.out.println("Your horse has finished " + (winners.indexOf(choice)+1));
    }
}
