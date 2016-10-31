package com.devsar.android.ucbindings.providers;

import rx.subjects.PublishSubject;

/**
 * Created by Juan on 30/8/16.
 */
public final class NoCacheProvider<T> extends SubjectProvider<PublishSubject<T>> {

    private static <T> PublishSubject<T> createSubject() {
        return PublishSubject.create();
    }

    public NoCacheProvider() {
        super(createSubject());
    }

    @Override
    protected PublishSubject<T> buildSubject() {
        return createSubject();
    }
}
