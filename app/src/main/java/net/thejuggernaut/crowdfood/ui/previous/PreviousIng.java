package net.thejuggernaut.crowdfood.ui.previous;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.thejuggernaut.crowdfood.R;
import net.thejuggernaut.crowdfood.api.Ingredients;
import net.thejuggernaut.crowdfood.api.Name;

import java.text.SimpleDateFormat;
import java.util.Date;

import static net.thejuggernaut.crowdfood.ui.scan.DisplayFuncs.setColour;

public class PreviousIng extends AppCompatActivity {
    Ingredients p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_ing);
        Intent intent = getIntent();
        p = (Ingredients) intent.getSerializableExtra("PRODUCTINGREDIENTS");
        LinearLayout main = (LinearLayout) findViewById(R.id.prevIngLayout);
        for(Ingredients i : p.getChanges()){
            //Create a linearlayout
            LinearLayout l = new LinearLayout(this);
            l.setOrientation(LinearLayout.VERTICAL);
            //set it up
            setup(l,i);
            //add to main
            main.addView(l);
        }

    }

    private void setup(LinearLayout l, Ingredients p){
        //Set the background colour
        setColour(l,p.getVotes());

        //Value
        TextView val = new TextView(this);
        if(p.getIngredients() == null){
            val.setText(Html.fromHtml("<h2> {empty} </h2>"));
        }else {
            val.setText(Html.fromHtml("<h2>" + TextUtils.join(",", p.getIngredients()) + "</h2>"));
        }
        l.addView(val);

        //Timestamp text
        long stmp = p.getStamp();
        Date d = new java.util.Date(stmp*1000);
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        TextView ts = new TextView(this);
        ts.setText(Html.fromHtml("<i>Edit: "+f.format(d)+"</i>"));
        l.addView(ts);


    }
}
