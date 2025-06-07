package com.example.danew.retrofit;

import com.example.danew.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

interface AuthAPI {
    @POST("api/auth/login")
    fun save(@Body user:User): Call<User>
}
