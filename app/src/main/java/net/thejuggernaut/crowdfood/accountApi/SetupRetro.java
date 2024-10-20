package net.thejuggernaut.crowdfood.accountApi;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SetupRetro implements Serializable {
    public static final String BASE_URL = "http://192.168.1.102:8001";

    public static AccountApi getRetro(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        AccountApi foodieAPI = retrofit.create(AccountApi.class);
        return foodieAPI;
    }
}
