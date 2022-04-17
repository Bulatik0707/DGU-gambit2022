package com.example.gambit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.gambit.api.RetrofitApi;
import com.example.gambit.api.ApiService;
import com.example.gambit.example.FoodData;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private final RecyclerView.LayoutManager layoutManager=new LinearLayoutManager( this );
    private GambitAdapter gambitAdapter;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        setActionBarTitle ();

        ApiService apiService= RetrofitApi.CreateService ( ApiService.class );
        Call<List<FoodData>> call=apiService.getProductsList( RetrofitApi.PAGE );
        call.enqueue ( new Callback<List<FoodData>>() {
            @Override
            public void onResponse(@NonNull Call<List<FoodData>> call,
                                   @NonNull Response<List<FoodData>> response ) {
                if (response.isSuccessful ()) {
                    List<FoodData> foodData =response.body ();
                    recyclerView=findViewById ( R.id.recyclerView );
                    recyclerView.setHasFixedSize ( true );
                    gambitAdapter =new GambitAdapter(foodData);
                    recyclerView.setLayoutManager ( layoutManager );
                    recyclerView.setAdapter (gambitAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<FoodData>> call,
                                  @NonNull Throwable t ) {
            }
        } );
    }

    private void setActionBarTitle() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions ( ActionBar.DISPLAY_SHOW_CUSTOM );
        getSupportActionBar ().setCustomView ( R.layout.glavnaya_layout);
    }

    public void Click(View view) {
        Intent intent = new Intent(MainActivity.this, ForestActivity.class);
        startActivity(intent);

    }
}