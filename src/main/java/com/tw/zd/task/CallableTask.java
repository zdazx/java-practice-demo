package com.tw.zd.task;

import lombok.AllArgsConstructor;

import java.util.concurrent.Callable;

@AllArgsConstructor
public class CallableTask implements Callable<Integer> {
    private final int number;

    public Integer call() {
        int factor = 1;
        for (int i = number; i > 1; i--) {
            factor = factor * i;
        }
        return factor;
    }
}
