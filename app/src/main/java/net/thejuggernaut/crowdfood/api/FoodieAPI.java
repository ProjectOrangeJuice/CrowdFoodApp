package net.thejuggernaut.crowdfood.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FoodieAPI {

    @GET("product/{barcode}")
    Call<Product> loadProduct(@Path("barcode") String status);

}
