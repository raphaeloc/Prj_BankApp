package com.example.prj_bankapp.api;

import com.example.prj_bankapp.model.listmodel.Statement;
import com.example.prj_bankapp.model.usermodel.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BankAPI {

    @FormUrlEncoded
    @POST("login/")
    Call<User> login(
            @Field("user")  String username,
            @Field("password") String password);

    @GET("statements/{idUser}")
    Call<Statement> list(
            @Path("idUser") String id);
}
