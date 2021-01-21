package com.tw.zd.rxjava.example7;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import org.reactivestreams.Subscription;

public class FlowableExample {
    public static void main(String[] args) {
        Flowable.just(1, 2, 3)
                .subscribe(new FlowableSubscriber<Integer>() {
                    private Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription subscription) {
                        System.out.println("flowable-start");
                        this.subscription = subscription;
                        subscription.request(1);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println(integer);
                        this.subscription.request(1);
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("flowable-complete");
                    }
                });
    }
}
