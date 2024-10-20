package net.thejuggernaut.crowdfood.ui.previous;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.thejuggernaut.crowdfood.R;
import net.thejuggernaut.crowdfood.api.Name;
import net.thejuggernaut.crowdfood.api.Nutrition;
import net.thejuggernaut.crowdfood.ui.scan.TableTextWatch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static net.thejuggernaut.crowdfood.ui.scan.DisplayFuncs.setColour;

public class PreviousNutrition extends AppCompatActivity {
    Nutrition p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_nutrition);

        Intent intent = getIntent();
        p = (Nutrition) intent.getSerializableExtra("PRODUCTNUTRITION");
        LinearLayout main = (LinearLayout) findViewById(R.id.prevNutritionLayout);
        for(Nutrition i : p.getChanges()){
            //Create a linearlayout
            LinearLayout l = new LinearLayout(this);
            l.setOrientation(LinearLayout.VERTICAL);
            //set it up
            setup(l,i);
            //add to main
            main.addView(l);
        }

    }

    private void setup(LinearLayout l, Nutrition p){
        //Set the background colour
        setColour(l,p.getVotes());
        SharedPreferences pref = getSharedPreferences("AccountInfo",MODE_PRIVATE);
        String storedHashMapString = pref.getString("Recommended", "");
        java.lang.reflect.Type type = new TypeToken<HashMap<String, Float>>(){}.getType();
        Gson gson = new Gson();
        Map<String, Float> map = gson.fromJson(storedHashMapString, type);
        Map<String, Float> nodeMap =
                new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        if(map != null) {
            nodeMap.putAll(map);
        }
        //Value

        TableLayout tbl = new TableLayout(this);
        l.addView(tbl);
        //headings
        TableRow r = new TableRow(this);
        if (p.getWeight() != "") {
            TextView nutName = new TextView(this);
            nutName.setText("Weight ");
            r.addView(nutName);
        }
            TextView val1 = new TextView(this);
            val1.setText(p.getWeight());
            r.addView(val1);
            if (p.getRecommended() != "") {
                TextView rpo = new TextView(this);
                rpo.setText("Recommended portion ");
                r.addView(rpo);
            }
            TextView val2 = new TextView(this);
            val2.setText(p.getRecommended());
            r.addView(val2);





        tbl.addView(r);
        if (p.getNutrition() == null) {
            Map<String, float[]> m = new HashMap<>();
            p.setNutrition(m);
        }
        for (Map.Entry<String, float[]> entry : p.getNutrition().entrySet()) {
            TableRow curRow = new TableRow(this);
            EditText ent2 = new EditText(this);

                TextView ent = new TextView(this);
                ent.setText(entry.getKey());
                curRow.addView(ent);




            EditText val = new EditText(this);
            val.setText(String.valueOf(entry.getValue()[0]));


            curRow.addView(val);








                val.setEnabled(false);
                EditText valr = new EditText(this);
                valr.setText(String.valueOf(entry.getValue()[1]));
                curRow.addView(valr);
                valr.setEnabled(false);

                if(nodeMap.containsKey(entry.getKey())){
                    TextView rda = new TextView(this);
                    System.out.println(entry.getKey()+" is "+entry.getValue()[1]+"/"+nodeMap.get(entry.getKey()));
                    float percentage = (entry.getValue()[1] /nodeMap.get(entry.getKey()))*100;
                    double p2 = Math.round(percentage*10)/10.0;
                    rda.setText(p2+"%");
                    curRow.addView(rda);
                }

            tbl.addView(curRow);

        }


        //Timestamp text
        long stmp = p.getStamp();
        Date d = new java.util.Date(stmp*1000);
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        TextView ts = new TextView(this);
        ts.setText(Html.fromHtml("<i>Edit: "+f.format(d)+"</i>"));
        l.addView(ts);


    }
}
