# UCBindings
Bind RxJava observables to your activity and fragment lifecycle.

[![Release](https://jitpack.io/v/devsar/ucbindings.svg)](https://jitpack.io/#devsar/ucbindings)

## Avoid

- Leaking context and RxJava subscriptions
- Subscribing to observables in your views
- Missing data because the app is in background

## How it works

UCBindings uses RxJava subjects to provide caching, replay and other subject behavior when your views become active.

![Binding engine internals](http://cloud.devsar.com/index.php/apps/files_sharing/ajax/publicpreview.php?x=1600&y=405&a=true&file=ucbindings-engine-docs.png&t=TesbXVo75CjxJz3&scalingup=0)

The Binding objects represent your use cases on behalf of your view, and they manage how the information flows to the view, according to the selected subject behavior.
Currently, UCBindings works with:
  - **AsyncSubject:** Allows caching of the last item only
  - **BehaviorSubject:** Allows caching of the last item
  - **PublishSubject:** Does not cache at all
  - **ReplaySubject:** Caches everything
  
## Usage

Create a Binding object in your activity and initialize it

```java
public class MainActivity extends BoundActivity { // Extend BoundActivity to get lifecycle goodies

    private TextView lblHello;

    private MainViewModel viewModel;
    // Declare the use case binding
    private Binding usersBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lblHello = (TextView) findViewById(R.id.lblHello);
        viewModel = new MainViewModel();

        // Create a binding from your provider
        usersBinding = BindingBuilder.boundTo(viewModel.usersProvider)
                // Provide RxJava callbacks
                .onNext(users -> {
                    // Display users on screen here
                    lblHello.setText(users.get(0).getUsername());
                    Toast.makeText(this, "Users received", Toast.LENGTH_SHORT).show();
                })
                .onError(error -> Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show())
                .onCompleted(() -> Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show())
                // specifies a binding that will be alive only until the source observable completes
                .oneTime();
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
```

Create a SubjectProvider object to link the RxJava subscription to the binding engine

```java
public class MainViewModel {

    private API api;

    // Create a subject provider to provide the subscribed subject to the binding
    public LastOnlyProvider<List<UserModel>> usersProvider;

    public MainViewModel() {
        api = new API();
        usersProvider = new LastOnlyProvider<>();
    }

    public void getUsers() {
        api.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // Subscribe the subject to the use case observable when firing request
                .subscribe(usersProvider.getSubject());
    }
}
```

## Download

Add jitpack as a repository in your top-level `build.gradle` file

```gradle
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```

Add the UCBindings dependency to your module-level `build.gradle` file

```gradle
dependencies {
    ...
    compile 'com.github.devsar:ucbindings:x.y.z'
    ...
}
```

