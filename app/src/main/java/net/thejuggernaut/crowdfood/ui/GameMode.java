package net.thejuggernaut.crowdfood.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import net.thejuggernaut.crowdfood.R;
import net.thejuggernaut.crowdfood.gameApi.Game;
import net.thejuggernaut.crowdfood.gameApi.GameApi;
import net.thejuggernaut.crowdfood.gameApi.SetupGame;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameMode extends AppCompatActivity {
    String session = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode);
        SharedPreferences pref = getSharedPreferences("Game", Context.MODE_PRIVATE);

        session = pref.getString("Session","");
        if(session.equals("")){
            finish();
        }


    }

    public void endGame(View v){
        SharedPreferences pref = getSharedPreferences("Game", Context.MODE_PRIVATE);
        GameApi gameAPI = SetupGame.getRetro(this);
        Call<Void> call = gameAPI.endGame(session);
        call.enqueue(new Callback<Void>() { @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if(response.isSuccessful()) {

                SharedPreferences pref = getSharedPreferences("Game", Context.MODE_PRIVATE);
                pref.edit().putString("Session","").apply();
                finish();

            } else {

                Log.i("Game api",response.message());
            }
        }


            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }
}
