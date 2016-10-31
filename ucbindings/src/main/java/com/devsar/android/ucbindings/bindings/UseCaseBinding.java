package com.devsar.android.ucbindings.bindings;

import com.devsar.android.ucbindings.providers.SubjectProvider;

import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subjects.Subject;

/**
 * Class used bind a view to an application use case (login, signup, get profile information, etc).
 *
 * Concrete bindings are provided so as to be able to bind to different kinds of subjects
 */
class UseCaseBinding<S extends Subject<T, T>, T> implements Binding {

    final Action1<T> onNext;
    final Action0 onCompleted;
    final Action1<Throwable> onError;
    private final SubjectProvider<S> subjectProvider;
    private Subscription subscription;

    /**
     *
     * @param onNext Rx Action to execute when there are results available.
     *                 It is called every time there's new data available (same as onNext)
     * @param onError Rx Action to execute when the source observable fails.
     * @param onCompleted Rx Action to execute when the source observable has completed.
     * @param subjectProvider Subject Provider used to perform subscription and link data to the view.
     *                        It is also used to reconstruct the subject when on error or completion,
     *                        this is for the user to be able to refire the use case on the cases
     *                        mentioned above.
     */
    UseCaseBinding(Action1<T> onNext, Action1<Throwable> onError,
                   Action0 onCompleted, SubjectProvider<S> subjectProvider) {
        this.onNext = onNext;
        this.onCompleted = onCompleted;
        this.onError = onError;
        this.subjectProvider = subjectProvider;
    }

    /**
     * Binds the use case to the view.
     */
    @Override
    public void bind() {
        subscribe(subjectProvider.getSubject());
    }

    /**
     * Actually creates the link between the view and the subject.
     *
     * @param subject The Subject to subscribe the view to
     */
    protected void subscribe(S subject) {
        subscribe(subject, onNext, onError, onCompleted);
    }

    protected final void subscribe(S subject, Action1<T> onNext,
                             Action1<Throwable> onError, Action0 onCompleted) {
        subscription = subject.subscribe(
                onNext,
                error -> {
                    onError.call(error);
                    resubscribe();
                },
                onCompleted
        );
    }

    /**
     * Resubscribes to the use case by means of a new subject, built using the subject provider.
     */
    private void resubscribe() {
        subscribe(subjectProvider.rebuildSubject());
    }

    /**
     * Breaks subscription between subject and view.
     */
    @Override
    public void unbind() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }
}
