package com.devsar.android.ucbindings.providers;

import rx.subjects.BehaviorSubject;

/**
 * Concrete Subject Provider to be used with Rx BehaviorSubject.
 */
public final class BehaviorSubjectProvider<T> extends SubjectProvider<BehaviorSubject<T>> {

    /**
     *
     * @param <T> The type of the data provided by the subject.
     * @return A new instance of a BehaviorSubject.
     */
    private static <T> BehaviorSubject<T> createSubject() {
        return BehaviorSubject.create();
    }

    /**
     * Constructs an instance with a subject.
     */
    public BehaviorSubjectProvider() {
        super(createSubject());
    }

    @Override
    protected BehaviorSubject<T> getNewSubjectInstance() {
        return createSubject();
    }
}
