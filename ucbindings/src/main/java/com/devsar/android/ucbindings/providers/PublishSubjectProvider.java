package com.devsar.android.ucbindings.providers;

import rx.subjects.PublishSubject;

/**
 * Created by Juan on 30/8/16.
 */
public final class PublishSubjectProvider<T> extends SubjectProvider<PublishSubject<T>> {

    private static <T> PublishSubject<T> createSubject() {
        return PublishSubject.create();
    }

    public PublishSubjectProvider() {
        super(createSubject());
    }

    @Override
    protected PublishSubject<T> getNewSubjectInstance() {
        return createSubject();
    }
}
