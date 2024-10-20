package net.thejuggernaut.crowdfood.ui.scan;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import net.thejuggernaut.crowdfood.R;
import net.thejuggernaut.crowdfood.api.Product;

import org.w3c.dom.Text;

import java.util.Map;

public class DisplayProduct extends AppCompatActivity {
    private Product p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent = getIntent();
        p = (Product) intent.getSerializableExtra("PRODUCT");


        setupProductName();
        setupIngredients();
        setupNutrition();
    }


    private void setupProductName(){
        TextView pn = (TextView) findViewById(R.id.productName);
        pn.setText(p.getProductName().getName());

        if(p.getProductName().getUp() < 5){
            ((LinearLayout) findViewById(R.id.productNameVoteLayout)).setVisibility(View.VISIBLE);
            Button up = (Button) findViewById(R.id.productNameUp);
            up.setText("Up: "+p.getProductName().getUp());
            Button down = (Button) findViewById(R.id.productNameDown);
            down.setText("Down: "+p.getProductName().getDown());
        }

    }

    private void setupIngredients(){
        TextView pn = (TextView) findViewById(R.id.ingredients);

        pn.setText( TextUtils.join(",",p.getIngredients().getIngredients()));

        if(p.getIngredients().getUp() < 5){
            ((LinearLayout) findViewById(R.id.ingredientsVoteLayout)).setVisibility(View.VISIBLE);
            Button up = (Button) findViewById(R.id.ingredientsUp);
            up.setText("Up: "+p.getIngredients().getUp());
            Button down = (Button) findViewById(R.id.ingredientsDown);
            down.setText("Down: "+p.getIngredients().getDown());
        }

    }

    private void setupNutrition(){
        TableLayout tbl = (TableLayout) findViewById(R.id.nutritionTable);
        //headings
        TableRow r = new TableRow(this);
        TextView nutName = new TextView(this);
        nutName.setText("Typical values");
        r.addView(nutName);
        TextView val1 = new TextView(this);
        val1.setText("Per..");
        r.addView(val1);

        TextView val2 = new TextView(this);
        val2.setText(p.getServing().getServing());
        r.addView(val2);
        tbl.addView(r);
        for(Map.Entry<String, Float> entry: p.getNutrition().getNutrition().entrySet()){
            TableRow curRow = new TableRow(this);
            TextView ent = new TextView(this);
            ent.setText(entry.getKey());
            curRow.addView(ent);

            EditText val = new EditText(this);
            val.setEnabled(false);
            val.setText(String.valueOf(entry.getValue()));
            curRow.addView(val);

            tbl.addView(curRow);

        }




        if(p.getNutrition().getUp() < 5){
            ((LinearLayout) findViewById(R.id.nutritionVoteLayout)).setVisibility(View.VISIBLE);
            Button up = (Button) findViewById(R.id.nutritionUp);
            up.setText("Up: "+p.getIngredients().getUp());
            Button down = (Button) findViewById(R.id.nutritionDown);
            down.setText("Down: "+p.getIngredients().getDown());
        }

    }
}
