package com.example.gambit.api;

import com.example.gambit.example.FoodData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("39")
    Call<List<FoodData>> getProductsList(@Query  ( "page" ) Integer page);

}