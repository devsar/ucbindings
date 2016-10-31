package com.devsar.android.ucbindings.providers;

import rx.subjects.Subject;

/**
 * Used by bindings and provided by view models, it is used to encapsulate the subject creation
 * and reconstruction in an object, to be able to have multiple instances of subjects without
 * turning the view model into a mess.
 */
public abstract class SubjectProvider<S extends Subject<?, ?>> {

    private S subject;

    /**
     * @param subject The Subject that will be provided by this instance.
     */
    SubjectProvider(S subject) {
        this.subject = subject;
    }

    /**
     *
     * @return The subject which this instance provides.
     */
    public S getSubject() {
        return subject;
    }

    /**
     *
     * @return An instance of a rebuilt subject which will by default be subscribed to the
     * same use cases as its predecessor was.
     */
    public S rebuildSubject() {
        subject = buildSubject();
        return subject;
    }

    /**
     *
     * @return A new subject instance
     */
    protected abstract S buildSubject();
}
