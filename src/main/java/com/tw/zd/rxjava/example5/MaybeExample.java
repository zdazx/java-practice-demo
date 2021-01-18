package com.tw.zd.rxjava.example5;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class MaybeExample {
    public static void main(String[] args) {
        getObservableWithMaybe();
        getObservableWithEmptyMaybe();
    }

    private static void getObservableWithEmptyMaybe() {
        Maybe.empty()
                .subscribe(new MaybeObserver<Object>() {
                    public void onSubscribe(@NonNull Disposable disposable) {
                        System.out.println("empty-maybe-start");
                    }

                    public void onSuccess(@NonNull Object o) {

                    }

                    public void onError(@NonNull Throwable throwable) {

                    }

                    public void onComplete() {
                        System.out.println("empty-maybe-complete");
                    }
                });
    }

    private static void getObservableWithMaybe() {
        Maybe.just(1)
                .subscribe(new MaybeObserver<Integer>() {
                    public void onSubscribe(@NonNull Disposable disposable) {
                        System.out.println("maybe-start");
                    }

                    public void onSuccess(@NonNull Integer integer) {
                        System.out.println(integer);
                    }

                    public void onError(@NonNull Throwable throwable) {

                    }

                    public void onComplete() {
                        System.out.println("maybe-complete");
                    }
                });

    }

}
