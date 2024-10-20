package net.thejuggernaut.crowdfood.accountApi;

import net.thejuggernaut.crowdfood.api.Product;
import net.thejuggernaut.crowdfood.api.Vote;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AccountApi {

    @GET("account")
    Call<Info> loadAccount();

    @POST("account")
    Call<Void> updateAccount(@Body Info info);

    @GET("account/points")
    Call<Points> loadPoints();
}
