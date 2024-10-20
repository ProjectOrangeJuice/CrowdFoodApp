package net.thejuggernaut.crowdfood.ui.previous;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.thejuggernaut.crowdfood.R;
import net.thejuggernaut.crowdfood.api.Name;
import net.thejuggernaut.crowdfood.ui.scan.DisplayFuncs;
import net.thejuggernaut.crowdfood.api.Product;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

import static net.thejuggernaut.crowdfood.ui.scan.DisplayFuncs.setColour;

public class PreviousName extends AppCompatActivity {
    Name p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_name);

        TextView pn = (TextView) findViewById(R.id.productName);
        EditText pne = (EditText) findViewById(R.id.productNameEdit);
        Intent intent = getIntent();
        p = (Name) intent.getSerializableExtra("PRODUCTNAME");
        LinearLayout main = (LinearLayout) findViewById(R.id.prevNameLayout);
        for(Name i : p.getChanges()){
            //Create a linearlayout
            LinearLayout l = new LinearLayout(this);
            l.setOrientation(LinearLayout.VERTICAL);
            //set it up
            setup(l,i);
            //add to main
            main.addView(l);
        }

    }

    private void setup(LinearLayout l, Name p){
        //Set the background colour
        setColour(l,p.getVotes());

        //Value
        TextView val = new TextView(this);
        val.setText(Html.fromHtml("<h2>"+p.getName()+"</h2>"));
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
