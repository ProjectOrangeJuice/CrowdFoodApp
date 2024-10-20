package net.thejuggernaut.crowdfood.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SetupRetro {
    public static final String BASE_URL = "http://192.168.1.126:8000";

    public static FoodieAPI getRetro(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        FoodieAPI foodieAPI = retrofit.create(FoodieAPI.class);
        return foodieAPI;
    }
}
