package com.k4rnaj1k.task2;

import com.k4rnaj1k.Task;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Task2 implements Task {
    public void execute() {
        FutureTask<List<Boolean>> task = new FutureTask<>(new CountThread(List.of(1, 2, 3, 5, 17)));
        try {
            Thread t = new Thread(task);
            t.start();
            List<Boolean> resultList = task.get();
            int count = 0;
            for (Boolean res :
                    resultList) {
                if (res) {
                    count++;
                }
            }
            System.out.println("Total simple numbers count " + count);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
