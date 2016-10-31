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
  - **AsyncSubject:** Allows caching of the last item emitted when the app is in background
  - **BehaviorSubject:** Allows similar caching but with a seed and different onCompleted behavior
  - **PublishSubject:** Allows publish behavior, does not cache
  - **ReplaySubject:** Allows the binding to handle the whole history of items emitted
  
## Usage

Create a Binding object in your activity and initialize it

```java
public class MainActivity extends BoundActivity { // Extend BoundActivity to get automatic subscribe/unsubscribe

    private MainViewModel viewModel;
    private Binding someBinding;
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new MainViewModel();

        // Create a binding based on an AsyncSubject from RxJava
        someBinding = BindingFactory.INSTANCE
                // Provide the SubjectProvider to be subscribed to the use case observable
                .async(viewModel.someSubjectProvider)
                // Provide RxJava callbacks
                .onNext(data ->
                    // Display data on screen here
                    Toast.makeText(this, "Data received", Toast.LENGTH_SHORT).show()
                )
                .onError(error -> Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show())
                .onCompleted(() -> Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show())
                // Create Binding object
                .build();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // Fire use case whenever you like
        viewModel.getUsers();
    }

    @Override
    protected Binding[] getBindings() {
        // Provide the bindings you use to bind to activity lifecycle
        return new Binding[] { someBinding };
    }
}
```

Create a SubjectProvider object to link the RxJava subscription to the binding engine

```java
public class MainViewModel {

    private API api;

    // Create a subject provider
    public AsyncSubjectProvider<List<SomeModel>> someSubjectProvider;

    public MainViewModel() {
        someSubjectProvider = new AsyncSubjectProvider<>();
    }

    public void getUsers() {
        api.getData()
                .last()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // Subscribe the subject to the use case observable when firing request
                .subscribe(someSubjectProvider.getSubject());
    }
```

**Note:** Both the SubjectProvider and the Binding must be configured to use the same kind of subject. In the above example it is AsyncSubject, so the Binding is built using `BindingFactory.Instance.async(SubjectProvider provider)` and the SubjectProvider is an instance of `AsyncSubjectProvider`

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

