package com.devsar.android.ucbindingssample;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Juan on 24/10/16.
 */

public interface API {

    @GET("users")
    Observable<List<UserModel>> getUsers();
}
