package com.k4rnaj1k.task2;

import com.k4rnaj1k.Task;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Task2 implements Task {

    List<? extends Number> list;

    public Task2(List<? extends Number> list) {
        this.list = list;
    }

    public void execute() {
        FutureTask<List<Boolean>> task = new FutureTask<>(new CountThread(list));
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
