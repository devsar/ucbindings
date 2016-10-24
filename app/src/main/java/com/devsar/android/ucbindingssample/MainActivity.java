package com.devsar.android.ucbindingssample;

import android.os.Bundle;
import android.widget.Toast;

import com.devsar.android.ucbindings.bindings.Binding;
import com.devsar.android.ucbindings.bindings.BindingFactory;
import com.devsar.android.ucbindings.bindings.BoundActivity;

public class MainActivity extends BoundActivity { // Extend BoundActivity to get lifecycle goodies

    private MainViewModel viewModel;
    // Declare the use case binding
    private Binding usersBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new MainViewModel();

        // Create a binding based on an AsyncSubject from RxJava
        usersBinding = BindingFactory.INSTANCE
                // Provide the SubjectProvider to be subscribed to the use case observable
                .async(viewModel.usersProvider)
                // Provide RxJava callbacks
                .onNext(users ->
                    // Display users on screen here
                    Toast.makeText(this, "Users received", Toast.LENGTH_SHORT).show()
                )
                .onError(error -> Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show())
                .onCompleted(() -> Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show())
                // Create Binding object
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Fire use case whenever you like without creating subscriptions in your view
        viewModel.getUsers();
    }

    @Override
    protected Binding[] getBindings() {
        // Provide the bindings you use to bind to activity/fragment lifecycle
        return new Binding[] { usersBinding };
    }
}
