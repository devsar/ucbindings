package com.devsar.android.ucbindings.bindings;

import com.devsar.android.ucbindings.providers.SubjectProvider;

import rx.functions.Action0;
import rx.functions.Action1;
import rx.subjects.Subject;

/**
 * Created by Juan on 20/10/16.
 */

public class BindingBuilder<S extends Subject<T, T>, T> {

    private Action1<T> onResult = someT -> {};
    private Action1<Throwable> onError = e -> {};
    private Action0 onCompleted = () -> {};
    private boolean oneTime = false;
    private SubjectProvider<S> provider;

    public BindingBuilder(SubjectProvider<S> provider) {
        this.provider = provider;
    }

    public BindingBuilder<S, T> onNext(Action1<T> callback) {
        onResult = callback;
        return this;
    }

    public BindingBuilder<S, T> onError(Action1<Throwable> callback) {
        onError = callback;
        return this;
    }

    public BindingBuilder<S, T> onCompleted(Action0 callback) {
        onCompleted = callback;
        return this;
    }

    public BindingBuilder<S, T> oneTime() {
        oneTime = true;
        return this;
    }

    public Binding build() {
        if (oneTime) {
            return new OneTimeBinding<>(onResult, onError, onCompleted, provider);
        } else {
            return new UseCaseBinding<>(onResult, onError, onCompleted, provider);
        }
    }
}
