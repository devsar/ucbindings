package com.devsar.android.ucbindingssample;

import com.devsar.android.ucbindings.providers.AsyncSubjectProvider;
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
    public AsyncSubjectProvider<List<UserModel>> usersProvider;

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
        usersProvider = new AsyncSubjectProvider<>();
    }

    public void getUsers() {
        api.getUsers()
                .last()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // Subscribe the subject to the use case observable when firing request
                .subscribe(usersProvider.getSubject());
    }
}
