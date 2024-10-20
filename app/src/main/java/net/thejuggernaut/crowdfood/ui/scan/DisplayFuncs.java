package net.thejuggernaut.crowdfood.ui.scan;


import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import net.thejuggernaut.crowdfood.R;
import net.thejuggernaut.crowdfood.api.FoodieAPI;
import net.thejuggernaut.crowdfood.api.Product;
import net.thejuggernaut.crowdfood.api.SetupRetro;
import net.thejuggernaut.crowdfood.api.Trust;
import net.thejuggernaut.crowdfood.api.Vote;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayFuncs {

    Product p;
    public DisplayFuncs(Product p){
        this.p = p;
    }

    public void voteButtons(ImageButton up, ImageButton down, int[] voteArray){

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vote(voteArray[0],voteArray[1],voteArray[2]);
                up.setImageResource(R.drawable.ic_thumb_up_green_24dp);
                down.setVisibility(View.GONE);
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vote(-voteArray[0],-voteArray[1],-voteArray[2]);
                down.setImageResource(R.drawable.ic_thumb_down_green_24dp);
                up.setVisibility(View.GONE);
            }
        });
    }


    public void setColour(LinearLayout l, Trust t){
        int dif = Math.abs(t.trustDown - t.trustUp);
        if(dif <= 30){
            //Set to red. We don't know which
            l.setBackgroundResource(R.drawable.rounded_red);
        }else if (t.trustUp >= 80){
            //set to yellow
            l.setBackgroundResource(R.drawable.rounded_green);
        }else if (t.trustUp >= 40){
            //Set to green
            l.setBackgroundResource(R.drawable.rounded_yellow);
        }else{
            //set to red
            l.setBackgroundResource(R.drawable.rounded_red);
        }
    }


    private void vote(int name, int ingre, int nut){
        Vote v = new Vote(p.getID(),name,ingre,nut);

        FoodieAPI foodieAPI = SetupRetro.getRetro();
        Call<Void> call = foodieAPI.voteForProduct(v);
        call.enqueue(new Callback<Void>() { @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if(response.isSuccessful()) {
                Log.i("UPDATE","WORKED");
            } else {
                //not found
                Log.i("UPDATE",response.message());
            }
        }


            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }



}
