package com.tw.zd.task;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SchedulersTest {
    private String result = "";
    @Test
    void should_schedule_a_job_when_created_schedule_worker() throws InterruptedException {
        Scheduler scheduler = Schedulers.single();
        Scheduler.Worker worker = scheduler.createWorker();
        worker.schedule(() -> result += "action");

        Thread.sleep(1000);
        assertEquals("action", result);
    }

    @Test
    void should_cancel_the_rest_of_work_queue_when_call_unsubscribe() throws InterruptedException {
        Scheduler scheduler = Schedulers.newThread();
        Scheduler.Worker worker = scheduler.createWorker();
        worker.schedule(() -> {
           result += "_first_action";
           worker.dispose();
        });
        worker.schedule(() -> result+="_second_action");

        Thread.sleep(1000);
        assertEquals("_first_action", result);
    }
}
