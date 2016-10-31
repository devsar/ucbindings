package com.devsar.android.ucbindings.bindings;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Juan on 20/10/16.
 */

abstract class AbstractBindingBuilder<T> implements Binding.Builder<T> {

    Action1<T> onResult = someT -> {};
    Action1<Throwable> onError = e -> {};
    Action0 onCompleted = () -> {};

    @Override
    public Binding.Builder<T> onNext(Action1<T> callback) {
        onResult = callback;
        return this;
    }

    @Override
    public Binding.Builder<T> onError(Action1<Throwable> callback) {
        onError = callback;
        return this;
    }

    @Override
    public Binding.Builder<T> onCompleted(Action0 callback) {
        onCompleted = callback;
        return this;
    }
}
