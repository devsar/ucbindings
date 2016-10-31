package com.devsar.android.ucbindings.bindings;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Juan on 29/8/16.
 */
public interface Binding {
    void bind();
    void unbind();

    interface Builder<T> {
        Builder<T> onNext(Action1<T> callback);
        Builder<T> onError(Action1<Throwable> callback);
        Builder<T> onCompleted(Action0 callback);
        Binding build();
    }
}
