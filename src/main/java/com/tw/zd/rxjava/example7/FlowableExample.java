package com.tw.zd.rxjava.example7;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableSubscriber;
import org.reactivestreams.Subscription;

public class FlowableExample {
    public static void main(String[] args) {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> flowableEmitter) throws Exception {
                flowableEmitter.onNext(1);
                flowableEmitter.onNext(2);
                flowableEmitter.onNext(3);
            }
        }, BackpressureStrategy.BUFFER)
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
                    }
                });
    }
}
