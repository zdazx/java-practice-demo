package com.tw.zd.task;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SchedulersTest {
    private String result = "";
    private String result1 = "";
    private String result2 = "";
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

    @Test
    void should_execute_a_task_with_a_new_thread() throws InterruptedException {
        Observable.just("Hello")
                .observeOn(Schedulers.newThread())
                .doOnNext(item -> result1 += Thread.currentThread().getName())
                .observeOn(Schedulers.newThread())
                .subscribe(item -> result2 += Thread.currentThread().getName());

        Thread.sleep(1000);
        assertEquals("RxNewThreadScheduler-1", result1);
        assertEquals("RxNewThreadScheduler-2", result2);
    }

    @Test
    void should_execute_tasks_of_work_queue() throws InterruptedException {
        Scheduler scheduler = Schedulers.newThread();
        Scheduler.Worker worker = scheduler.createWorker();
        worker.schedule(() -> {
            result += Thread.currentThread().getName() + "_Start";
            worker.schedule(() -> result += "_worker_");
            result += "_End";
        });
        Thread.sleep(3000);
        assertEquals("RxNewThreadScheduler-1_Start_End_worker_", result);
    }
}
