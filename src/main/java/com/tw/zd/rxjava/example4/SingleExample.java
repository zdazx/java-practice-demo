package com.tw.zd.rxjava.example4;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class SingleExample {
    public static void main(String[] args) {
        Single.just(1)
                .subscribe(new SingleObserver<Integer>() {
                    public void onSubscribe(@NonNull Disposable disposable) {

                    }

                    public void onSuccess(@NonNull Integer integer) {
                        System.out.println(integer);
                    }

                    public void onError(@NonNull Throwable throwable) {

                    }
                });
    }

}
