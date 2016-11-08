package com.devsar.android.ucbindingssample;

import com.devsar.android.ucbindings.UCB;
import com.devsar.android.ucbindings.providers.CacheLastProvider;
import com.devsar.android.ucbindings.providers.LastOnlyProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Juan on 21/10/16.
 */

public class MainViewModel {

    private Retrofit retrofit;
    private API api;

    // Create a subject provider to provide the subscribed subject to the binding
    public final LastOnlyProvider<List<UserModel>> usersProvider;
    public final CacheLastProvider<String> timeProvider;

    public MainViewModel() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        api = retrofit.create(API.class);
        usersProvider = new LastOnlyProvider<>();
        timeProvider = new CacheLastProvider<>();
    }

    public void getUsers() {
        api.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // Subscribe the subject to the use case observable when firing request
                .subscribe(usersProvider.getSubject());
    }

    public void startTimer() {
        UCB.timer(tick -> "Ticks: " + tick)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(timeProvider.getSubject());
    }
}
