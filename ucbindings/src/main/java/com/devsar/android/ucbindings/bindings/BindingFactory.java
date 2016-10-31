package com.devsar.android.ucbindings.bindings;

import com.devsar.android.ucbindings.providers.LastOnlyProvider;
import com.devsar.android.ucbindings.providers.CacheLastProvider;
import com.devsar.android.ucbindings.providers.NoCacheProvider;
import com.devsar.android.ucbindings.providers.CacheAllProvider;

/**
 * Created by Juan on 20/10/16.
 */

public class BindingFactory {

    public static final BindingFactory instance = new BindingFactory();

    private BindingFactory() {}

    /**
     * @param provider The subject provider to use at binding time.
     *                 This should be provided by the view model
     * @return An async binding builder to customize binding callbacks
     */
    public <T> Binding.Builder<T> async(LastOnlyProvider<T> provider) {
        return new AsyncBindingBuilder<>(provider);
    }

    /**
     * @param provider The subject provider to use at binding time.
     *                 This should be provided by the view model
     * @return A behavior binding builder to customize binding callbacks
     */
    public <T> Binding.Builder<T> behavior(CacheLastProvider<T> provider) {
        return new BehaviorBindingBuilder<>(provider);
    }

    /**
     * @param provider The subject provider to use at binding time.
     *                 This should be provided by the view model
     * @return A publish binding builder to customize binding callbacks
     */
    public <T> Binding.Builder<T> publish(NoCacheProvider<T> provider) {
        return new PublishBindingBuilder<>(provider);
    }

    /**
     * @param provider The subject provider to use at binding time.
     *                 This should be provided by the view model
     * @return A replay binding builder to customize binding callbacks
     */
    public <T> Binding.Builder<T> replay(CacheAllProvider<T> provider) {
        return new ReplayBindingBuilder<>(provider);
    }




    private class AsyncBindingBuilder<T> extends AbstractBindingBuilder<T> {

        private final LastOnlyProvider<T> provider;

        private AsyncBindingBuilder(LastOnlyProvider<T> provider) {
            this.provider = provider;
        }

        @Override
        public Binding build() {
            return new UseCaseBinding<>(onResult, onError, onCompleted, provider);
        }
    }

    private class BehaviorBindingBuilder<T> extends AbstractBindingBuilder<T> {

        private final CacheLastProvider<T> provider;

        private BehaviorBindingBuilder(CacheLastProvider<T> provider) {
            this.provider = provider;
        }

        @Override
        public Binding build() {
            return new UseCaseBinding<>(onResult, onError, onCompleted, provider);
        }
    }

    private class PublishBindingBuilder<T> extends AbstractBindingBuilder<T> {

        private final NoCacheProvider<T> provider;

        private PublishBindingBuilder(NoCacheProvider<T> provider) {
            this.provider = provider;
        }

        @Override
        public Binding build() {
            return new UseCaseBinding<>(onResult, onError, onCompleted, provider);
        }
    }

    private class ReplayBindingBuilder<T> extends AbstractBindingBuilder<T> {

        private final CacheAllProvider<T> provider;

        private ReplayBindingBuilder(CacheAllProvider<T> provider) {
            this.provider = provider;
        }

        @Override
        public Binding build() {
            return new UseCaseBinding<>(onResult, onError, onCompleted, provider);
        }
    }
}
