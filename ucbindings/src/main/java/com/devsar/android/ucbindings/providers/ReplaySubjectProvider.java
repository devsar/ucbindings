package com.devsar.android.ucbindings.providers;

import rx.subjects.ReplaySubject;

/**
 * Concrete Subject Provider to use with Rx ReplaySubject.
 */
public final class ReplaySubjectProvider<T> extends SubjectProvider<ReplaySubject<T>> {

    /**
     *
     * @param capacity The capacity of the new subject (0 for "infinite").
     * @param <T> The type of the data provided by the subject.
     * @return A new subject instance with a given capacity.
     */
    private static <T> ReplaySubject<T> createSubject(int capacity) {
        return capacity <= 0 ? ReplaySubject.create() : ReplaySubject.create(capacity);
    }

    private final int capacity;

    /**
     * Constructs an instance with a subject with an infinite capacity.
     */
    public ReplaySubjectProvider() {
        super(ReplaySubject.create());
        capacity = 0;
    }

    /**
     * Constructs an instance with a subject with a given capacity.
     * @param capacity The capacity of the subject that will be provided.
     */
    public ReplaySubjectProvider(int capacity) {
        super(createSubject(capacity));
        this.capacity = capacity;
    }

    @Override
    protected ReplaySubject<T> getNewSubjectInstance() {
        return createSubject(capacity);
    }
}
