package com.tw.zd.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {

    private ExecutorService executorService;

    @BeforeEach
    void setUp() {
        executorService = Executors.newSingleThreadExecutor();
    }

    @Test
    void should_return_value_in_future_when_submitting_a_callable_task() throws ExecutionException, InterruptedException {
        CallableTask task = new CallableTask(5);
        Future<Integer> future = executorService.submit(task);
        executorService.shutdown();
        assertEquals(120, future.get().intValue());
    }
}