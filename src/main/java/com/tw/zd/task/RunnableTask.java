package com.tw.zd.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunnableTask implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(RunnableTask.class);

    public void run() {
        logger.info("this is the runnable task");
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new RunnableTask());
        executorService.shutdown();
    }
}
