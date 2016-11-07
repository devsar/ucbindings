package com.devsar.android.ucbindings.bindings;

import com.devsar.android.ucbindings.providers.SubjectProvider;

import rx.functions.Action0;
import rx.functions.Action1;
import rx.subjects.Subject;

/**
 * Used to build a binding with Rx callbacks and other binding configurations
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

    /**
     * Specifies what function to call when value arrives
     * @param callback the Rx onNext function
     * @return a Builder configured with the onNext function set
     */
    public BindingBuilder<S, T> onNext(Action1<T> callback) {
        onResult = callback;
        return this;
    }

    /**
     * Specifies what function to call when an error occurs
     * @param callback the Rx onError function
     * @return a Builder with the onError function set
     */
    public BindingBuilder<S, T> onError(Action1<Throwable> callback) {
        onError = callback;
        return this;
    }

    /**
     * Specifies what function to call when use case finishes
     * @param callback the Rx onCompleted function
     * @return a Builder with the onCompleted function set
     */
    public BindingBuilder<S, T> onCompleted(Action0 callback) {
        onCompleted = callback;
        return this;
    }

    /**
     * Specifies that the binding should be made just one time. When the use case finishes,
     * the binding will not bind when bind() method is called.
     * @return a Builder which will build a OneTimeBinding
     */
    public BindingBuilder<S, T> oneTime() {
        oneTime = true;
        return this;
    }

    /**
     * Builds the configured binding
     * @return a fully functional Binding working as specified by the builder
     */
    public Binding build() {
        if (oneTime) {
            return new OneTimeBinding<>(onResult, onError, onCompleted, provider);
        } else {
            return new UseCaseBinding<>(onResult, onError, onCompleted, provider);
        }
    }
}
