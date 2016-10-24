package com.devsar.android.ucbindings.providers;

import rx.subjects.AsyncSubject;

/**
 * Concrete Subject Provider to be used with Rx AsyncSubject.
 */
public final class AsyncSubjectProvider<T> extends SubjectProvider<AsyncSubject<T>> {

    /**
     *
     * @param <T> The type of the data provided by the subject.
     * @return A new instance of an AsyncSubject.
     */
    private static <T> AsyncSubject<T> createSubject() {
        return AsyncSubject.create();
    }

    /**
     * Constructs an instance with a subject.
     */
    public AsyncSubjectProvider() {
        super(createSubject());
    }

    @Override
    protected AsyncSubject<T> getNewSubjectInstance() {
        return createSubject();
    }
}
