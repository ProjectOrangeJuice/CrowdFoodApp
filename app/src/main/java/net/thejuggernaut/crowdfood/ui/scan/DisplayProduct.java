package net.thejuggernaut.crowdfood.ui.scan;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import net.thejuggernaut.crowdfood.R;
import net.thejuggernaut.crowdfood.api.FoodieAPI;
import net.thejuggernaut.crowdfood.api.Product;
import net.thejuggernaut.crowdfood.api.SetupRetro;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayProduct extends AppCompatActivity {
    private Product p;
    private boolean editMode = false;
    ArrayList<EditText> items;
    ArrayList<EditText> values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent = getIntent();
        p = (Product) intent.getSerializableExtra("PRODUCT");


        setupProductName();
        setupIngredients();
        setupNutrition();

        //Enable edit button
        Button btn = (Button) findViewById(R.id.editButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMode(v);
            }
        });

    }


    private void editMode(View v){
        if(editMode){
            save();
        }
        editMode = !editMode;
        setupProductName();
        setupIngredients();
        setupNutrition();
    }

    private void setupProductName() {
        TextView pn = (TextView) findViewById(R.id.productName);
        EditText pne = (EditText) findViewById(R.id.productNameEdit);
        if (!editMode) {
            pn.setVisibility(View.VISIBLE);
            pne.setVisibility(View.GONE);
            pn.setText(p.getProductName().getName());
            if (p.getProductName().getUp() < 5) {

                ((LinearLayout) findViewById(R.id.productNameVoteLayout)).setVisibility(View.VISIBLE);
                Button up = (Button) findViewById(R.id.productNameUp);
                up.setText("Up: " + p.getProductName().getUp());
                Button down = (Button) findViewById(R.id.productNameDown);
                down.setText("Down: " + p.getProductName().getDown());
            }

        }else{
           pn.setVisibility(View.GONE);
           pne.setVisibility(View.VISIBLE);
            ((LinearLayout) findViewById(R.id.productNameVoteLayout)).setVisibility(View.GONE);
           pne.setText(p.getProductName().getName());
        }

    }

    private void setupIngredients() {
        TextView pn = (TextView) findViewById(R.id.ingredients);
        EditText pne = (EditText) findViewById(R.id.ingredientsEdit);

        pn.setText(TextUtils.join(",", p.getIngredients().getIngredients()));
        pne.setText(TextUtils.join(",", p.getIngredients().getIngredients()));
        if(!editMode) {
            pn.setVisibility(View.VISIBLE);
            pne.setVisibility(View.GONE);
            if (p.getIngredients().getUp() < 5) {
                ((LinearLayout) findViewById(R.id.ingredientsVoteLayout)).setVisibility(View.VISIBLE);
                Button up = (Button) findViewById(R.id.ingredientsUp);
                up.setText("Up: " + p.getIngredients().getUp());
                Button down = (Button) findViewById(R.id.ingredientsDown);
                down.setText("Down: " + p.getIngredients().getDown());
            }
        }else{
            ((LinearLayout) findViewById(R.id.ingredientsVoteLayout)).setVisibility(View.GONE);
            pn.setVisibility(View.GONE);
            pne.setVisibility(View.VISIBLE);
        }
    }

    private void setupNutrition() {

        items = new ArrayList<>();
       values = new ArrayList<>();

        TableLayout tbl = (TableLayout) findViewById(R.id.nutritionTable);
        tbl.removeAllViews();
        //headings
        TableRow r = new TableRow(this);
        TextView nutName = new TextView(this);
        nutName.setText("Typical values");
        r.addView(nutName);
        TextView val1 = new TextView(this);
        val1.setText(p.getNutrition().getWeight());
        r.addView(val1);

        TextView val2 = new TextView(this);
        val2.setText(p.getNutrition().getRecommended());
        r.addView(val2);
        tbl.addView(r);
        for (Map.Entry<String, Float[]> entry : p.getNutrition().getNutrition().entrySet()) {
            TableRow curRow = new TableRow(this);
            if(!editMode) {
                TextView ent = new TextView(this);
                ent.setText(entry.getKey());
                curRow.addView(ent);
            }else{
                EditText ent = new EditText(this);
                ent.setText(entry.getKey());
                curRow.addView(ent);
                items.add(ent);
            }



            EditText val = new EditText(this);
            EditText recomm = new EditText(this);
            if(!editMode) {
                val.setEnabled(false);
                recomm.setEnabled(false);
            }
            values.add(val);
            values.add(recomm);
            val.setText(String.valueOf(entry.getValue()[0]));
            recomm.setText(String.valueOf(entry.getValue()[1]));
            curRow.addView(val);
            curRow.addView(recomm);

            tbl.addView(curRow);

        }

        //Add our empty row
        if(editMode){
            TableRow curRow = new TableRow(this);
            EditText ent = new EditText(this);
            curRow.addView(ent);
            items.add(ent);
            EditText val = new EditText(this);
            values.add(val);
            curRow.addView(val);
            tbl.addView(curRow);
        }

        if(!editMode) {
            if (p.getNutrition().getUp() < 5) {
                ((LinearLayout) findViewById(R.id.nutritionVoteLayout)).setVisibility(View.VISIBLE);
                Button up = (Button) findViewById(R.id.nutritionUp);
                up.setText("Up: " + p.getIngredients().getUp());
                Button down = (Button) findViewById(R.id.nutritionDown);
                down.setText("Down: " + p.getIngredients().getDown());
            }
        }

    }

    private void save(){
        EditText pne = (EditText) findViewById(R.id.productNameEdit);
        EditText inge = (EditText) findViewById(R.id.ingredientsEdit);
        Boolean changed = false;
        if(pne.getText().toString() != p.getProductName().getName()){
            changed = true;
            p.getProductName().setName(pne.getText().toString());
        }

        if(inge.getText().toString().split(",") != p.getIngredients().getIngredients()){
            changed = true;
            p.getIngredients().setIngredients(inge.getText().toString().split(","));
        }

        if(changed){
            FoodieAPI foodieAPI = SetupRetro.getRetro();
            Call<Void> call = foodieAPI.updateProduct(p);
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
}
