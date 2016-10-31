package com.devsar.android.ucbindings.bindings;

import com.devsar.android.ucbindings.providers.SubjectProvider;

import rx.functions.Action0;
import rx.functions.Action1;
import rx.subjects.Subject;

/**
 * Created by Juan on 31/10/16.
 */

final class OneTimeBinding<S extends Subject<T, T>, T> extends UseCaseBinding<S, T> {

    private boolean finished = false;

    /**
     * @param onResult        Rx Action to execute when there are results available.
     *                        It is called every time there's new data available (same as onNext)
     * @param onError         Rx Action to execute when the source observable fails.
     * @param onCompleted     Rx Action to execute when the source observable has completed.
     * @param subjectProvider Subject Provider used to perform subscription and link data to the view.
     *                        It is also used to reconstruct the subject when on error or completion,
     *                        this is for the user to be able to refire the use case on the cases
     */
    OneTimeBinding(Action1<T> onResult, Action1<Throwable> onError,
                   Action0 onCompleted, SubjectProvider<S> subjectProvider) {
        super(onResult, onError, onCompleted, subjectProvider);
    }

    @Override
    public void bind() {
        if (!finished) super.bind();
    }

    @Override
    protected void subscribe(S subject) {
        subscribe(subject, onNext, onError, () -> {
            onCompleted.call();
            finished = true;
        });
    }
}
