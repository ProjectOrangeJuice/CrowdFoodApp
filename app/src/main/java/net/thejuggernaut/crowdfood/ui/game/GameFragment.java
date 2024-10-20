package net.thejuggernaut.crowdfood.ui.game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import net.thejuggernaut.crowdfood.R;
import net.thejuggernaut.crowdfood.gameApi.Game;
import net.thejuggernaut.crowdfood.gameApi.GameApi;
import net.thejuggernaut.crowdfood.gameApi.SetupGame;
import net.thejuggernaut.crowdfood.ui.GameMode;
import net.thejuggernaut.crowdfood.ui.scan.DisplayProduct;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameFragment extends Fragment {

    private View v;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_game, container, false);
        v = root;

        setupHistory();
        setupButtons();
        return root;
    }


    private void setupButtons(){
        ((Button) v.findViewById(R.id.gameButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
    }

private  void setupHistory(){
    GameApi gameAPI = SetupGame.getRetro(getContext());
    Call<Game[]> call = gameAPI.getGames();
    call.enqueue(new Callback<Game[]>() { @Override
    public void onResponse(Call<Game[]> call, Response<Game[]> response) {
        if(response.isSuccessful()) {
            displayGames(response.body());

        } else {

            Log.i("Game api",response.message());
        }
    }


        @Override
        public void onFailure(Call<Game[]> call, Throwable t) {
            t.printStackTrace();
        }

    });
}

private void displayGames(Game[] games){
    LinearLayout ml = (LinearLayout) v.findViewById(R.id.gameHisLayout);
    if(games != null) {
        for (Game g : games) {
            LinearLayout l = new LinearLayout(getContext());
            //Timestamp text
            long stmp = g.getStamp();
            Date d = new java.util.Date(stmp * 1000);
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");

            TextView stamp = new TextView(getContext());
            stamp.setText(f.format(d));

            TextView points = new TextView(getContext());
            points.setText("You got " + g.getPoints() + "!");
            ml.addView(l);
        }

    }
}


private void startGame(){
    GameApi gameAPI = SetupGame.getRetro(getContext());
    Call<Game> call = gameAPI.getSession();
    call.enqueue(new Callback<Game>() { @Override
    public void onResponse(Call<Game> call, Response<Game> response) {
        if(response.isSuccessful()) {

            SharedPreferences pref = getContext().getSharedPreferences("Game", Context.MODE_PRIVATE);
            pref.edit().putString("Session",response.body().getSession()).apply();
            Intent intent = new Intent(getView().getContext(), GameMode.class);
            startActivity(intent);

        } else {

            Log.i("Game api",response.message());
        }
    }


        @Override
        public void onFailure(Call<Game> call, Throwable t) {
            t.printStackTrace();
        }

    });
}


}