package net.thejuggernaut.crowdfood.ui.game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
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

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of fragement");
        setupHistory();
        super.onResume();
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
    ml.removeAllViews();
    if(games != null) {

        for (Game g : games) {
            LinearLayout l = new LinearLayout(getContext());
            //Timestamp text
            long stmp = g.getStamp();
            Date d = new java.util.Date(stmp * 1000);

            long stmp2 = g.getEndStamp();
            Date d2 = new java.util.Date(stmp2 * 1000);
            long diff = d2.getTime() - d.getTime();
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            TextView complete = new TextView(getContext());
            if(!g.isActive()) {
                complete.setText(Html.fromHtml("At <b> " + f.format(d) + " </b>you started a game that lasted " +
                        TimeUnit.MILLISECONDS.toMinutes(diff) +
                        " minutes and got <i>" + g.getPoints() + " </i>points!"));
            }else{
                complete.setText(Html.fromHtml("At <b> " + f.format(d) + " </b>you started a game that " +
                        "currently has <i>" + g.getPoints() + " </i>points!"));
            }


            l.addView(complete);

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