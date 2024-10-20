package net.thejuggernaut.crowdfood.gameApi;

import net.thejuggernaut.crowdfood.api.Product;
import net.thejuggernaut.crowdfood.api.Vote;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GameApi {



    @GET("session")
    Call<Game> getSession();

    @GET("games")
    Call<Game[]> getGames();

    @GET("question/{session}")
    Call<Question> loadQuestion(@Path("session") String status);

    @POST("play")
    Call<PlayResult> playQuestion(@Body Play play);

    @DELETE("end/{session}")
    Call<Void> endGame(@Path("session") String status);


}
