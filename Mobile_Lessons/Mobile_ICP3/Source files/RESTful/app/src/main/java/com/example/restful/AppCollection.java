package com.example.restful;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AppCollection {

    @GET("users")
    Call<List<user>> getData();
}
