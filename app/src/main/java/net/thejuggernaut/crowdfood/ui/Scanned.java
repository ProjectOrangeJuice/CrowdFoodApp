package net.thejuggernaut.crowdfood.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.thejuggernaut.crowdfood.R;
import net.thejuggernaut.crowdfood.api.Product;

public class Scanned extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned);

        Intent intent = getIntent();
        Product p = (Product) intent.getSerializableExtra("PRODUCT");


        setupWarnings();
        setupIngredients(p.getIngredients());
    }


    private void setupWarnings(){
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.scanDisplay);

        LinearLayout warningsLayout = new LinearLayout(this);

        TextView text = new TextView(this);
        text.setText("An example warning");

        warningsLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        warningsLayout.setPadding(0,10,0,10);
        warningsLayout.setBackgroundColor(Color.RED);
        warningsLayout.addView(text);

        mainLayout.addView(warningsLayout);


    }


    private void setupIngredients(String[] ingredients){

    }
}
