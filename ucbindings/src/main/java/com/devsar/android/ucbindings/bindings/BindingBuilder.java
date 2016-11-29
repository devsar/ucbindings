package com.devsar.android.ucbindings.bindings;

import com.devsar.android.ucbindings.providers.SubjectProvider;

import rx.functions.Action0;
import rx.functions.Action1;
import rx.subjects.Subject;

/**
 * Used to build a binding with Rx callbacks and other binding configurations
 */
public class BindingBuilder<S extends Subject<T, T>, T> {

    public static <S extends Subject<T, T>, T> BindingBuilder<S, T> boundTo(SubjectProvider<S> provider) {
        return new BindingBuilder<>(provider);
    }

    private Action1<T> onNext = someT -> {};
    private Action1<Throwable> onError = e -> {};
    private Action0 onCompleted = () -> {};
    private SubjectProvider<S> provider;

    private BindingBuilder(SubjectProvider<S> provider) {
        this.provider = provider;
    }

    /**
     * Specifies what function to call when value arrives
     * @param callback the Rx onNext function
     * @return a Builder configured with the onNext function set
     */
    public BindingBuilder<S, T> onNext(Action1<T> callback) {
        onNext = callback;
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
     * Builds a binding which should be made just one time. When the use case finishes,
     * the binding will not bind when bind() method is called.
     * @return a OneTimeBinding
     */
    public Binding oneTime() {
        return new OneTimeBinding<>(onNext, onError, onCompleted, provider);
    }

    /**
     * Builds the configured binding
     * @return a fully functional Binding working as specified by the builder
     */
    public Binding build() {
        return new UseCaseBinding<>(onNext, onError, onCompleted, provider);
    }
}
