package com.example.gambit.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApi {

    public static final String BASE_URL = "https://api.gambit-app.ru/category/";
    public static final int PAGE = 1;

    public static <T> T CreateService(Class<T> serviceClass){

        Retrofit retrofit = new Retrofit.Builder ()
                .baseUrl ( RetrofitApi.BASE_URL )
                .addConverterFactory ( GsonConverterFactory.create () )
                .build ();

        return retrofit.create ( serviceClass );

    }

}