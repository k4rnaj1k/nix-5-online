package com.k4rnaj1k.task1;

public class HelloThread extends Thread {
    int threadNumber;

    public HelloThread(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    public void run() {
        System.out.println("Hello from thread " + threadNumber);
    }
}
