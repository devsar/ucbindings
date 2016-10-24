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
final class UseCaseBinding<S extends Subject<T, T>, T> implements Binding {

    private final Action1<T> onResult;
    private final Action0 onCompleted;
    private final Action1<Throwable> onError;
    private final SubjectProvider<S> subjectProvider;
    private Subscription subscription;

    /**
     *
     * @param onResult Rx Action to execute when there are results available.
     *                 It is called every time there's new data available (same as onNext)
     * @param onError Rx Action to execute when the source observable fails.
     * @param onCompleted Rx Action to execute when the source observable has completed.
     * @param subjectProvider Subject Provider used to perform subscription and link data to the view.
     *                        It is also used to reconstruct the subject when on error or completion,
     *                        this is for the user to be able to refire the use case on the cases
     *                        mentioned above.
     */
    UseCaseBinding(Action1<T> onResult, Action1<Throwable> onError,
                          Action0 onCompleted, SubjectProvider<S> subjectProvider) {
        this.onResult = onResult;
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
    private void subscribe(S subject) {
        subscription = subject.subscribe(
                onResult,
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
