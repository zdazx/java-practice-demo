package com.tw.zd.task;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

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
        worker.schedule(() -> result += "_second_action");

        Thread.sleep(1000);
        assertEquals("_first_action", result);
    }

    @Test
    void should_execute_a_task_with_a_new_thread() throws InterruptedException {
        Disposable disposable = Observable.just("Hello")
                .observeOn(Schedulers.newThread())
                .doOnNext(item -> result1 += Thread.currentThread().getName())
                .observeOn(Schedulers.newThread())
                .subscribe(item -> result2 += Thread.currentThread().getName());

        Thread.sleep(1000);
        disposable.dispose();

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

    @Test
    void should_execute_tasks_using_trampoline_scheduler() throws InterruptedException {
        Disposable disposable1 = Observable.just(2, 4, 6, 8)
                .subscribeOn(Schedulers.trampoline())
                .subscribe(i -> result += "" + i);
        Disposable disposable2 = Observable.just(1, 3, 5, 7)
                .subscribeOn(Schedulers.trampoline())
                .subscribe(i -> result += "" + i);

        Thread.sleep(1000);
        disposable1.dispose();
        disposable2.dispose();

        assertEquals("24681357", result);
    }

    @Test
    void should_execute_tasks_using_trampoline_scheduler_order() throws InterruptedException {
        Scheduler scheduler = Schedulers.trampoline();
        Scheduler.Worker worker = scheduler.createWorker();
        worker.schedule(() -> {
            result += Thread.currentThread().getName() + "_one_";
            worker.schedule(() -> {
                result += Thread.currentThread().getName() + "_two_";
                worker.schedule(() -> result += Thread.currentThread().getName() + "_three_");
                result += "_four_";
            });
            result += "_five_";
        });

        Thread.sleep(1000);
        assertEquals("main_one__five_main_two__four_main_three_", result);
    }

    @Test
    void should_execute_tasks_using_scheduler_by_from_factory() throws InterruptedException {
        ExecutorService poolA = Executors.newFixedThreadPool(5, threadFactory("Schedule-A-%d"));
        Scheduler schedulerA = Schedulers.from(poolA);

        ExecutorService poolB = Executors.newFixedThreadPool(5, threadFactory("Schedule-B-%d"));
        Scheduler schedulerB = Schedulers.from(poolB);

        Observable<String> observable = Observable.create(emitter -> {
            emitter.onNext("Hello");
            emitter.onNext("World");
            emitter.onComplete();
        });

        Disposable disposable = observable
                .subscribeOn(schedulerA)
                .subscribeOn(schedulerB)
                .subscribe(i -> result += Thread.currentThread().getName() + "_" + i + "_",
                        Throwable::printStackTrace,
                        () -> result += "_completed");

        Thread.sleep(1000);
        disposable.dispose();

        assertEquals("Schedule-A-0_Hello_Schedule-A-0_World__completed", result);
    }

    private ThreadFactory threadFactory(String pattern) {
        return new ThreadFactoryBuilder()
                .setNameFormat(pattern)
                .build();
    }

}
