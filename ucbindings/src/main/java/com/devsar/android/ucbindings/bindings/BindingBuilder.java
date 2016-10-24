package com.devsar.android.ucbindings.bindings;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Juan on 20/10/16.
 */

public interface BindingBuilder<T> {
    BindingBuilder<T> onNext(Action1<T> callback);
    BindingBuilder<T> onError(Action1<Throwable> callback);
    BindingBuilder<T> onCompleted(Action0 callback);
    Binding build();
}
