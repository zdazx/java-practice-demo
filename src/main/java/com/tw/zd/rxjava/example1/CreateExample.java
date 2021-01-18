package com.tw.zd.rxjava.example1;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class CreateExample {
    public static Observer<Integer> createObserver() {
        return new Observer<Integer>() {

            private Disposable disposable;

            public void onSubscribe(@NonNull Disposable disposable) {
                System.out.println("start");
                this.disposable = disposable;
            }

            public void onNext(@NonNull Integer s) {
                if (s == 2) {
//                    this.disposable.dispose();
                    System.out.println("isDisposed:" + this.disposable.isDisposed());
                }
                System.out.println(s);
            }

            public void onError(@NonNull Throwable throwable) {
                System.out.println("Error");
            }

            public void onComplete() {
                System.out.println("completed");
            }
        };
    }

    public static Observable<Integer> createObservable() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            public void subscribe(@NonNull ObservableEmitter<Integer> observableEmitter) {
                observableEmitter.onNext(1);
                observableEmitter.onNext(2);
                observableEmitter.onNext(3);
                observableEmitter.onComplete();
            }
        });
    }

    public static void main(String[] args) {
        createObservable().subscribe(createObserver());
    }


}
