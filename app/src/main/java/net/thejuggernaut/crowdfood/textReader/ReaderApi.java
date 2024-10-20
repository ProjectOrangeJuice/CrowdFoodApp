package net.thejuggernaut.crowdfood.textReader;

import net.thejuggernaut.crowdfood.api.Product;
import net.thejuggernaut.crowdfood.api.Vote;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReaderApi {



    @POST("ingredients")
    Call<IngText> getIngText(@Body IngText product);

    @POST("nutrition")
    Call<NText> getNutritionText(@Body NText product);


}
