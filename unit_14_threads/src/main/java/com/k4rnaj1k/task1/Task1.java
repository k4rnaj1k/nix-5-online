package com.k4rnaj1k.task1;

import com.k4rnaj1k.Task;

public class Task1 implements Task{
    public void execute() {
        for (int i = 49; i >= 0; i--) {
            Thread helloThread = new HelloThread(i);
            helloThread.start();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
