package com.k4rnaj1k;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Horse implements Runnable {
    private final CopyOnWriteArrayList<Integer> winners;
    private final Integer horseNumber;
    private Long distance = 0L;

    public Horse(CopyOnWriteArrayList<Integer> winners, Integer horseNumber) {
        this.winners = winners;
        this.horseNumber = horseNumber;
    }

    @Override
    public void run() {
        Random r = new Random();
        while (this.distance < 1000) {
            this.distance += r.nextInt(100) + 100;
            try {
                Thread.sleep(r.nextInt(100) + 400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.winners.add(this.horseNumber);
        System.out.println(horseNumber + " has finished the run.");
    }
}
