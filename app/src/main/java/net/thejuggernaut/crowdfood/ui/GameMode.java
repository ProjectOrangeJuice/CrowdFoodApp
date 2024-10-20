package net.thejuggernaut.crowdfood.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import net.thejuggernaut.crowdfood.R;
import net.thejuggernaut.crowdfood.gameApi.Game;
import net.thejuggernaut.crowdfood.gameApi.GameApi;
import net.thejuggernaut.crowdfood.gameApi.Question;
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

        generateQuestion();

    }




    private void generateQuestion(){
        SharedPreferences pref = getSharedPreferences("Game", Context.MODE_PRIVATE);
        if(pref.getString("question","").equals("")){
            //Get a new question
            getQuestion();
        }else{
            ((TextView) findViewById(R.id.questionText)).setText(pref.getString("question","oops, an error!"));
        }

    }


    private void getQuestion(){
        GameApi gameAPI = SetupGame.getRetro(this);
        SharedPreferences pref = getSharedPreferences("Game", Context.MODE_PRIVATE);
        Call<Question> call = gameAPI.loadQuestion(pref.getString("Session",""));
        call.enqueue(new Callback<Question>() { @Override
        public void onResponse(Call<Question> call, Response<Question> response) {
            if(response.isSuccessful()) {

                pref.edit().putString("question",response.body().getQuestion()).apply();
                ((TextView) findViewById(R.id.questionText)).setText(pref.getString("question","oops, an error!"));

            } else {

                Log.i("Game api",response.message());
            }
        }


            @Override
            public void onFailure(Call<Question> call, Throwable t) {
                t.printStackTrace();
            }

        });
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
