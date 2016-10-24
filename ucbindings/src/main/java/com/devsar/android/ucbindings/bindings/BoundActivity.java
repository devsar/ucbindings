package com.devsar.android.ucbindings.bindings;

import android.support.v7.app.AppCompatActivity;

/**
 * An activity that automatically binds and unbinds given use case bindings.
 * This is useful to avoid missing responses and unexpected behaviour when tha app is in background
 */
public abstract class BoundActivity extends AppCompatActivity {

    protected abstract Binding[] getBindings();

    @Override
    protected void onResume() {
        super.onResume();
        bindAll();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
