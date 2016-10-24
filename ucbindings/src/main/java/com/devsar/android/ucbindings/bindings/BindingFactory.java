package com.devsar.android.ucbindings.bindings;

import com.devsar.android.ucbindings.providers.AsyncSubjectProvider;
import com.devsar.android.ucbindings.providers.BehaviorSubjectProvider;
import com.devsar.android.ucbindings.providers.PublishSubjectProvider;
import com.devsar.android.ucbindings.providers.ReplaySubjectProvider;

/**
 * Created by Juan on 20/10/16.
 */

public class BindingFactory {

    public static final BindingFactory INSTANCE = new BindingFactory();

    private BindingFactory() {}

    /**
     * @param provider The subject provider to use at binding time.
     *                 This should be provided by the view model
     * @return An async binding builder to customize binding callbacks
     */
    public <T> BindingBuilder<T> async(AsyncSubjectProvider<T> provider) {
        return new AsyncBindingBuilder<>(provider);
    }

    /**
     * @param provider The subject provider to use at binding time.
     *                 This should be provided by the view model
     * @return A behavior binding builder to customize binding callbacks
     */
    public <T> BindingBuilder<T> behavior(BehaviorSubjectProvider<T> provider) {
        return new BehaviorBindingBuilder<>(provider);
    }

    /**
     * @param provider The subject provider to use at binding time.
     *                 This should be provided by the view model
     * @return A publish binding builder to customize binding callbacks
     */
    public <T> BindingBuilder<T> publish(PublishSubjectProvider<T> provider) {
        return new PublishBindingBuilder<>(provider);
    }

    /**
     * @param provider The subject provider to use at binding time.
     *                 This should be provided by the view model
     * @return A replay binding builder to customize binding callbacks
     */
    public <T> BindingBuilder<T> replay(ReplaySubjectProvider<T> provider) {
        return new ReplayBindingBuilder<>(provider);
    }




    private class AsyncBindingBuilder<T> extends AbstractBindingBuilder<T> {

        private final AsyncSubjectProvider<T> provider;

        private AsyncBindingBuilder(AsyncSubjectProvider<T> provider) {
            this.provider = provider;
        }

        @Override
        public Binding build() {
            return new UseCaseBinding<>(onResult, onError, onCompleted, provider);
        }
    }

    private class BehaviorBindingBuilder<T> extends AbstractBindingBuilder<T> {

        private final BehaviorSubjectProvider<T> provider;

        private BehaviorBindingBuilder(BehaviorSubjectProvider<T> provider) {
            this.provider = provider;
        }

        @Override
        public Binding build() {
            return new UseCaseBinding<>(onResult, onError, onCompleted, provider);
        }
    }

    private class PublishBindingBuilder<T> extends AbstractBindingBuilder<T> {

        private final PublishSubjectProvider<T> provider;

        private PublishBindingBuilder(PublishSubjectProvider<T> provider) {
            this.provider = provider;
        }

        @Override
        public Binding build() {
            return new UseCaseBinding<>(onResult, onError, onCompleted, provider);
        }
    }

    private class ReplayBindingBuilder<T> extends AbstractBindingBuilder<T> {

        private final ReplaySubjectProvider<T> provider;

        private ReplayBindingBuilder(ReplaySubjectProvider<T> provider) {
            this.provider = provider;
        }

        @Override
        public Binding build() {
            return new UseCaseBinding<>(onResult, onError, onCompleted, provider);
        }
    }
}
