package com.devsar.android.ucbindings.bindings;

import android.support.v4.app.Fragment;

/**
 * A fragment that automatically binds and unbinds given use case bindings.
 * This is useful to avoid missing responses and unexpected behaviour when tha app is in background
 */
public abstract class BoundFragment extends Fragment {

    protected abstract Binding[] getBindings();

    @Override
    public void onStart() {
        super.onStart();
        bindAll();
    }

    @Override
    public void onStop() {
        super.onStop();
        unbindAll();
    }

    /**
     * Binds all given use case bindings.
     */
    private void bindAll() {
        for (Binding binding : getBindings()) {
            binding.bind();
        }
    }

    /**
     * Unbinds all given use case bindings.
     */
    private void unbindAll() {
        for (Binding binding : getBindings()) {
            binding.unbind();
        }
    }
}
