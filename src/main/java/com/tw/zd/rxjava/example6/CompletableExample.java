package com.tw.zd.rxjava.example6;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class CompletableExample {
    public static void main(String[] args) {
        Completable.create(new CompletableOnSubscribe() {
            public void subscribe(@NonNull CompletableEmitter completableEmitter) {
                completableEmitter.onComplete();
            }
        }).subscribe(new CompletableObserver() {
            public void onSubscribe(@NonNull Disposable disposable) {

            }

            public void onComplete() {
                System.out.println("complete");
            }

            public void onError(@NonNull Throwable throwable) {

            }
        });
    }

}
