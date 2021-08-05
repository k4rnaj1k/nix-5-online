package com.k4rnaj1k.task2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class CountThread implements Callable<List<Boolean>> {
    List<? extends Number> list;

    public CountThread(List<? extends Number> list) {
        this.list = list;
    }

    @Override
    public List<Boolean> call() {
        ArrayList<Boolean> result = new ArrayList<>();
        for (Number n :
                list) {
            if (isSimple(n.doubleValue())) {
                result.add(true);
            } else {
                result.add(false);
            }
        }
        return result;
    }

    private boolean isSimple(double num) {
        if (num == 1) {
            System.out.println(num + " is not simple");
            return false;
        }
        double sqrt = Math.sqrt(num);
        for (int i = 2; i < sqrt; i++) {
            if (num % i == 0) {
                System.out.println(num + " is not simple");
                return false;
            }
        }
        System.out.println(num + " is simple");
        return true;
    }
}
