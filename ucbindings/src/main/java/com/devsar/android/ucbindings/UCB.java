package com.devsar.android.ucbindings;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Juan on 8/11/16.
 */

public class UCB {

    public static <T> Observable<T> timer(Func1<Long, T> mappingFunction, int seconds) {
        return Observable.interval(1, TimeUnit.SECONDS, Schedulers.computation())
                .take(seconds)
                .map(mappingFunction);
    }

    public static <T> Observable<T> timer(Func1<Long, T> mappingFunction) {
        return timer(mappingFunction, 60);
    }

    public static <T> Observable<T> poll(Observable<T> pollObservable, Func1<T, Boolean> until) {
        return Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                .flatMap(tick -> pollObservable)
                .takeUntil(until)
                .filter(until);
    }
}
