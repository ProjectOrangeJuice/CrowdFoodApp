package net.thejuggernaut.crowdfood.ui.scan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.thejuggernaut.crowdfood.R;
import net.thejuggernaut.crowdfood.api.FoodieAPI;
import net.thejuggernaut.crowdfood.api.Product;
import net.thejuggernaut.crowdfood.api.SetupRetro;
import net.thejuggernaut.crowdfood.ui.previous.PreviousName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayProduct extends AppCompatActivity {
    private Product p;
    private boolean editMode = false;
    ArrayList<EditText> items;
    ArrayList<EditText> values;
    DisplayFuncs tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_product);

        Intent intent = getIntent();
        p = (Product) intent.getSerializableExtra("PRODUCT");

        //Check nothing for null
        if (p.getIngredients().getIngredients() == null) {
            p.getIngredients().setIngredients(new String[0]);
        }

        tools = new DisplayFuncs(p,this);


        setupProductName();
        setupIngredients();
        setupNutrition();
        setupAlert();

        //Enable edit button
        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.editButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMode(v);
            }
        });

    }


    private void editMode(View v) {
        if (editMode) {
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
            tools.setColour(findViewById(R.id.productShape), p.getProductName().getVotes());

            if (p.getProductName().isVoteable()) {
                ((LinearLayout) findViewById(R.id.productNameVoteLayout)).setVisibility(View.VISIBLE);
                int[] vo = {1, 0, 0};
                tools.voteButtons(((ImageButton) findViewById(R.id.productNameUp)),
                        ((ImageButton) findViewById(R.id.productNameDown)), vo);
            } else {
                ((LinearLayout) findViewById(R.id.productNameVoteLayout)).setVisibility(View.GONE);
            }

            //Should we display history button?
            System.out.println("Size of  changes "+p.getProductName().getChanges().length);
            System.out.println(p.getProductName().getChanges());
            if(p.getProductName().getChanges().length > 0){
                //Yes
                ((ImageButton) findViewById(R.id.nameHisButton)).setVisibility(View.VISIBLE);
                ((ImageButton) findViewById(R.id.nameHisButton)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), PreviousName.class);
                        intent.putExtra("PRODUCTNAME",p.getProductName());
                        startActivity(intent);
                    }
                });
            }else{
                //no
                ((ImageButton) findViewById(R.id.nameHisButton)).setVisibility(View.GONE);
            }


        } else {
            pn.setVisibility(View.GONE);
            pne.setVisibility(View.VISIBLE);
            ((LinearLayout) findViewById(R.id.productNameVoteLayout)).setVisibility(View.GONE);
            pne.setText(p.getProductName().getName());
        }

    }

    private void setupAlert(){
        SharedPreferences pref= getSharedPreferences("AccountInfo",MODE_PRIVATE);
        String allergies = pref.getString("Allergies","");
        String[] allergiesList = allergies.split(",");

        ArrayList<String> alerts = new ArrayList<>();

        for(String val : p.getIngredients().getIngredients()){
            for(String al : allergiesList){
                if(val.toLowerCase().equals(al.toLowerCase()) && !val.equals("")){
                    alerts.add("Product contains "+al);
                }
            }
        }

        if(alerts.size() > 0){
            ((LinearLayout) findViewById(R.id.alertBox)).setVisibility(View.VISIBLE);
        }else{
            ((LinearLayout) findViewById(R.id.alertBox)).setVisibility(View.GONE);
        }

        String html = "<h1>Warning</h1>";
        for(String alert : alerts){
            html += "&#8226; "+alert+" <br>";
        }

        ((TextView) findViewById(R.id.alertText)).setText(Html.fromHtml(html));



    }


    private void setupIngredients() {
        TextView pn = (TextView) findViewById(R.id.ingredients);
        EditText pne = (EditText) findViewById(R.id.ingredientsEdit);
        if (p.getIngredients().getIngredients() != null) {
            pn.setText(TextUtils.join(",", p.getIngredients().getIngredients()));
            pne.setText(TextUtils.join(",", p.getIngredients().getIngredients()));
        } else {
            pn.setText("");
            pne.setText("");
        }
        if (!editMode) {
            pn.setVisibility(View.VISIBLE);
            pne.setVisibility(View.GONE);
            tools.setColour(findViewById(R.id.ingredientShape), p.getIngredients().getVotes());
            if (p.getIngredients().isVoteable()) {
                ((LinearLayout) findViewById(R.id.ingredientsVoteLayout)).setVisibility(View.VISIBLE);
                int[] vo = {0, 1, 0};
                tools.voteButtons(((ImageButton) findViewById(R.id.ingredientsUp)),
                        ((ImageButton) findViewById(R.id.ingredientsDown)), vo);
            } else {
                ((LinearLayout) findViewById(R.id.ingredientsVoteLayout)).setVisibility(View.GONE);
            }
        } else {
            ((LinearLayout) findViewById(R.id.ingredientsVoteLayout)).setVisibility(View.GONE);
            pn.setVisibility(View.GONE);
            pne.setVisibility(View.VISIBLE);
        }
    }

    EditText recommended;
    EditText weight;

    private void setupNutrition() {
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
        items = new ArrayList<>();
        values = new ArrayList<>();

        TableLayout tbl = (TableLayout) findViewById(R.id.nutritionTable);
        tbl.removeAllViews();
        //headings
        TableRow r = new TableRow(this);
        if (p.getNutrition().getWeight() != "" || editMode) {
            TextView nutName = new TextView(this);
            nutName.setText("Weight ");
            r.addView(nutName);
        }
        if (!editMode) {
            tools.setColour(findViewById(R.id.nutritionShape), p.getNutrition().getVotes());
            TextView val1 = new TextView(this);
            val1.setText(p.getNutrition().getWeight());
            r.addView(val1);
            if (p.getNutrition().getRecommended() != "") {
                TextView rpo = new TextView(this);
                rpo.setText("Recommended portion ");
                r.addView(rpo);
            }
            TextView val2 = new TextView(this);
            val2.setText(p.getNutrition().getRecommended());
            r.addView(val2);


        } else {
            EditText val1 = new EditText(this);
            val1.setText(p.getNutrition().getWeight());
            val1.setHint("100");
            r.addView(val1);


            TextView rpo = new TextView(this);
            rpo.setText("Recommended portion ");
            r.addView(rpo);


            EditText val2 = new EditText(this);
            val2.setHint("10");
            val2.setText(p.getNutrition().getRecommended());
            r.addView(val2);
            recommended = val2;
            weight = val1;
        }


        tbl.addView(r);
        if (p.getNutrition().getNutrition() == null) {
            Map<String, float[]> m = new HashMap<>();
            p.getNutrition().setNutrition(m);
        }
        for (Map.Entry<String, float[]> entry : p.getNutrition().getNutrition().entrySet()) {
            TableRow curRow = new TableRow(this);
            EditText ent2 = new EditText(this);
            if (!editMode) {
                TextView ent = new TextView(this);
                ent.setText(entry.getKey());
                curRow.addView(ent);

            } else {
                ent2.setText(entry.getKey());
                curRow.addView(ent2);
                items.add(ent2);
            }


            EditText val = new EditText(this);
            val.setText(String.valueOf(entry.getValue()[0]));


            curRow.addView(val);







            if (!editMode) {
                val.setEnabled(false);
                EditText valr = new EditText(this);
                valr.setText(String.valueOf(entry.getValue()[1]));
                curRow.addView(valr);
                valr.setEnabled(false);

                if(nodeMap.containsKey(entry.getKey())){
                    TextView rda = new TextView(this);
                    System.out.println(entry.getKey()+" is "+entry.getValue()[1]+"/"+nodeMap.get(entry.getKey()));
                    float percentage = (entry.getValue()[1] /nodeMap.get(entry.getKey()))*100;
                    double p = Math.round(percentage*10)/10.0;
                    rda.setText(p+"%");
                    curRow.addView(rda);
                }

            }
            values.add(val);

            TableTextWatch tbc = new TableTextWatch(items, values, curRow, ent2, val, this, tbl);
            val.addTextChangedListener(tbc);
            ent2.addTextChangedListener(tbc);


            tbl.addView(curRow);

        }

        //Add our empty row
        if (editMode) {
            TableRow curRow = new TableRow(this);
            EditText ent = new EditText(this);
            curRow.addView(ent);
            items.add(ent);
            EditText val = new EditText(this);
            values.add(val);
            ent.setHint("Fat");
            val.setHint("100");
            curRow.addView(val);
            tbl.addView(curRow);
            TableTextWatch tbc = new TableTextWatch(items, values, curRow, ent, val, this, tbl);
            val.addTextChangedListener(tbc);
            ent.addTextChangedListener(tbc);
        }

        if (!editMode) {
            if (p.getNutrition().isVoteable()) {
                ((LinearLayout) findViewById(R.id.nutritionVoteLayout)).setVisibility(View.VISIBLE);
                int[] vo = {0, 0, 1};
                tools.voteButtons(((ImageButton) findViewById(R.id.nutritionUp)),
                        ((ImageButton) findViewById(R.id.nutritionDown)), vo);
            } else {
                ((LinearLayout) findViewById(R.id.nutritionVoteLayout)).setVisibility(View.GONE);
            }
        } else {
            ((LinearLayout) findViewById(R.id.nutritionVoteLayout)).setVisibility(View.GONE);
        }

    }

    private boolean checkTable(Map<String, float[]> newmap) {
        System.out.println("Size.. " + newmap.size());
        if (newmap.size() != p.getNutrition().getNutrition().size()) {
            System.out.println("Diff size for table");
            return true;
        }

        for (Map.Entry<String, float[]> entry : p.getNutrition().getNutrition().entrySet()) {
            if (!newmap.containsKey(entry.getKey())) {
                System.out.println("I can't find a value so returning true");
                return true;
            }
            boolean found = false;
            for (Map.Entry<String, float[]> nentry : newmap.entrySet()) {
                if (nentry.getKey() == entry.getKey() &&
                        nentry.getValue()[0] == entry.getValue()[0]) {
                    found = true;
                }

            }
            if (!found) {
                System.out.println("I can't find a float so returning true");
                return true;
            }
            p.getNutrition().getNutrition().remove(entry.getKey());

        }
        return false;
    }


    private boolean checkIng(String[] ing) {
        String[] old = p.getIngredients().getIngredients();
        if (old.length != ing.length) {
            System.out.println("Ing diff size");
            return true;
        }
        for (String i : ing) {
            boolean found = false;
            for (String x : old) {
                if (x.equals(i)) {
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Could not find " + i);
                return true;
            }
        }
        return false;

    }

    private void save() {
        EditText pne = (EditText) findViewById(R.id.productNameEdit);
        EditText inge = (EditText) findViewById(R.id.ingredientsEdit);
        Boolean changed = false;
        if (!pne.getText().toString().equals(p.getProductName().getName())) {
            System.out.println("Update due to name.");

            changed = true;
            p.getProductName().setName(pne.getText().toString());
        }


        if (checkIng(inge.getText().toString().split(","))) {
            System.out.println("Update due to ing.");
            changed = true;
            p.getIngredients().setIngredients(inge.getText().toString().split(","));
        }


        //Get the new map of values
        Map<String, float[]> n = new HashMap<>();
        for (int i = 0; i < items.size() - 1; i++) {

            if (items.get(i).getText().toString().equals("")
                    && values.get(i).getText().toString().equals("")) {
                System.out.println("Empty value");
            } else {
                float[] floatvals = {Float.parseFloat(values.get(i).getText().toString()),
                        0};

                n.put(items.get(i).getText().toString(), floatvals);
                System.out.println("Put! " + n.size());
            }
        }

        System.out.println("CHecking.. " + n.size());
        if (!p.getNutrition().getRecommended().equals(recommended.getText().toString()) ||
                !p.getNutrition().getWeight().equals(weight.getText().toString()) ||
                checkTable(n)) {

            changed = true; //need to check to see if nutritional info has changed
            p.getNutrition().setRecommended(recommended.getText().toString());
            p.getNutrition().setWeight(weight.getText().toString());

            p.getNutrition().setNutrition(n);

        }


        if (changed) {
            FoodieAPI foodieAPI = SetupRetro.getRetro(this);
            Call<Product> call = foodieAPI.updateProduct(p,p.getID());
            call.enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    if (response.isSuccessful()) {
                        Log.i("UPDATE", "WORKED");
                        p = response.body();
                    } else {
                        //not found
                        Log.i("UPDATE", response.message());
                    }
                }


                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    t.printStackTrace();
                }

            });
        }
    }


}
