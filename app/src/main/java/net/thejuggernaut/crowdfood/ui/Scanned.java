package net.thejuggernaut.crowdfood.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import net.thejuggernaut.crowdfood.R;
import net.thejuggernaut.crowdfood.api.FoodieAPI;
import net.thejuggernaut.crowdfood.api.Product;
import net.thejuggernaut.crowdfood.api.SetupRetro;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Scanned extends AppCompatActivity {
    private Product p;
    private LinearLayout mainLayout;
    private boolean save = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned);

        Intent intent = getIntent();
        p = (Product) intent.getSerializableExtra("PRODUCT");

        //TODO
        //Setup handle for when there is no data (the getTrust will fail)

        setup();
    }

    public void goBack(View v){
      goBackin();
    }
    public void onBackPressed(){
        goBackin();
    }
    private void goBackin(){
        if(save){
            updateProduct();
        }
        finish();
    }

    private void makeEdit(EditText e, Button b){
        e.setEnabled(true);
        b.setText("Save");
        e.requestFocus();
        save = true;
    }
    private void undoMakeEdit(EditText e, Button b){
        e.setEnabled(false);
        b.setText("Edit");
    }

    private void setup(){
        mainLayout = (LinearLayout) findViewById(R.id.scanDisplay);
        mainLayout.removeAllViews();
        setupTitle(p.getProductName().getName());
        setupWarnings();
        setupIngredients(p.getIngredients().getIngredients());
        setupServing(p.getServing().getServing());
        setupNutritional(p.getNutrition().getNutrition());
    }


    private  void setupServing(final String ra){
       LinearLayout servingLayout = new LinearLayout(this);
        servingLayout.setOrientation(LinearLayout.VERTICAL);

        //Title
        TextView title = new TextView(this);
        title.setText("Recommend serving: ");
        final EditText rat = new EditText(this);
        rat.setText(ra);
        rat.setEnabled(false);
        servingLayout.addView(title);
        servingLayout.addView(rat);


        LinearLayout review = new LinearLayout(this);
        TextView upndown = new TextView(this);

        review.addView(upndown);
        final Button editThis = new Button(this);
        editThis.setText("Edit");
        editThis.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(rat.isEnabled()) {
                   undoMakeEdit(rat,editThis);
                    //p.setServing(rat.getText().toString());
                }else{
                    makeEdit(rat, editThis);
                }
            }
        });
        review.addView(editThis);


        servingLayout.addView(review);
        mainLayout.addView(servingLayout);

    }

    private void updateProduct(){
        FoodieAPI foodieAPI = SetupRetro.getRetro();
        Call<Product> call = foodieAPI.updateProduct(p.getID(),p);
        call.enqueue(new Callback<Product>() { @Override
        public void onResponse(Call<Product> call, Response<Product> response) {
            if(response.isSuccessful()) {
                Log.i("UPDATE","WORKED");
            } else {
                //not found
                Log.i("UPDATE",response.message());
            }
        }


            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }

    private void setupTitle(String name ){
         LinearLayout productNameLayout = new LinearLayout(this);
        productNameLayout.setOrientation(LinearLayout.VERTICAL);


        //Title
        TextView title = new TextView(this);
        title.setTextAppearance(android.R.style.TextAppearance_DeviceDefault_DialogWindowTitle);
        title.setText("Product name");
        productNameLayout.addView(title);

        final EditText tedit = new EditText(this);
        tedit.setTextAppearance(android.R.style.TextAppearance_DeviceDefault_DialogWindowTitle);
        tedit.setText(name);
        tedit.setEnabled(false);
        productNameLayout.addView(tedit);


        LinearLayout review = new LinearLayout(this);
        TextView upndown = new TextView(this);
        //upndown.setText("Up: "+point.getConfirm()+" Down:"+point.getDeny());
        review.addView(upndown);
        final Button editThis = new Button(this);
        editThis.setText("Edit");
        editThis.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               if(tedit.isEnabled()){
                  // p.setProductName(tedit.getText().toString());
                   undoMakeEdit(tedit,editThis);
               }else{
                   makeEdit(tedit,editThis);
               }
            }
        });
        review.addView(editThis);


        productNameLayout.addView(review);
        mainLayout.addView(productNameLayout);


    }



    private void setupWarnings(){

        LinearLayout warningsLayout = new LinearLayout(this);
        warningsLayout.setOrientation(LinearLayout.VERTICAL);

        TextView text = new TextView(this);
        text.setText("An example warning");

        warningsLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        warningsLayout.setPadding(0,10,0,10);
        warningsLayout.setBackgroundColor(Color.RED);
        warningsLayout.addView(text);

        mainLayout.addView(warningsLayout);


    }


    private void setupIngredients(String[] ingredients ){

        LinearLayout ingredientsLayout = new LinearLayout(this);
        ingredientsLayout.setOrientation(LinearLayout.VERTICAL);

        //Title
        TextView title = new TextView(this);
        title.setTextAppearance(android.R.style.TextAppearance_DeviceDefault_DialogWindowTitle);
        title.setText("Ingredients");
        ingredientsLayout.addView(title);

        //Ingredients list
        final EditText text = new EditText(this);
        text.setSingleLine(false);
        text.setElegantTextHeight(true);
        text.setEnabled(false);
        //Here we would bold any ing. that need warnings
        text.setText(TextUtils.join(", ",ingredients));

        ingredientsLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        ingredientsLayout.setPadding(0,10,0,10);
        ingredientsLayout.addView(text);


        LinearLayout review = new LinearLayout(this);
        TextView upndown = new TextView(this);
        //upndown.setText("Up: "+point.getConfirm()+" Down:"+point.getDeny());
        final Button takePhoto = new Button(this);
        takePhoto.setText("Take photo");
        takePhoto.setVisibility(View.GONE);
        review.addView(upndown);
        final Button editThis = new Button(this);
        editThis.setText("Edit");
        editThis.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(text.isEnabled()){
                   // p.setProductName(text.getText().toString());
                    undoMakeEdit(text,editThis);
                    takePhoto.setVisibility(View.GONE);
                }else{
                    makeEdit(text,editThis);
                    takePhoto.setVisibility(View.VISIBLE);
                }
            }
        });
        review.addView(editThis);
        review.addView(takePhoto);
        ingredientsLayout.addView(review);


        mainLayout.addView(ingredientsLayout);
    }



    private void setupNutritional(final Map<String,Float> nutrition ){
        LinearLayout nutritionLayout = new LinearLayout(this);
        nutritionLayout.setOrientation(LinearLayout.VERTICAL);

        //Title
        TextView title = new TextView(this);
        title.setTextAppearance(android.R.style.TextAppearance_DeviceDefault_DialogWindowTitle);
        title.setText("Nutrition");
        nutritionLayout.addView(title);

        //nutrition table
        final TableLayout tbl = new TableLayout(this);


        drawTable(nutrition,tbl, false);


        nutritionLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        nutritionLayout.setPadding(0,10,0,10);
        nutritionLayout.addView(tbl);


        LinearLayout review = new LinearLayout(this);
        TextView upndown = new TextView(this);
        //upndown.setText("Up: "+point.getConfirm()+" Down:"+point.getDeny());
        review.addView(upndown);
        final Button editThis = new Button(this);
        editThis.setText("Edit");
        editThis.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(tableInEdit){
                  tableInEdit = false;
                  Map<String, Float> tm =new HashMap<String, Float>();
                  for(int i = 0; i<values.size();i++){
                      if(!items.get(i).getText().toString().isEmpty() && !values.get(i).getText().toString().isEmpty())
                      tm.put(items.get(i).getText().toString(),Float.valueOf(values.get(i).getText().toString()));
                  }
                 // p.setNutrition(tm);

                    //drawTable(p.getNutrition(),tbl, false);
                    editThis.setText("Edit");
                }else{
                    tableInEdit = true;
                    drawTable(nutrition,tbl, true);
                    save = true;
                    editThis.setText("Save");
                }
            }
        });
        review.addView(editThis);
        nutritionLayout.addView(review);


        mainLayout.addView(nutritionLayout);
    }

    private ArrayList<EditText> items = new ArrayList<>();
    private ArrayList<EditText> values = new ArrayList<>();
    private Boolean tableInEdit = false;
    private int empty = 1;
    private void drawTable(Map<String,Float> nutrition,TableLayout tbl, Boolean editmode){
        items.clear();
        values.clear();
        tbl.removeAllViews();
        empty = 1;
        //Create headings
        TableRow thd = new TableRow(this);
        TextView it = new TextView(this);
        TextView st = new TextView(this);
        it.setText("Typical Values");
        st.setText("Per 100g");
        thd.addView(it);
        thd.addView(st);
        tbl.addView(thd);
        for(Map.Entry<String, Float> entry: nutrition.entrySet()){
            final TableRow tr = new TableRow(this);
            final EditText item = new EditText(this);
            items.add(item);
            item.setEnabled(editmode);
            final EditText value = new EditText(this);
            value.setEnabled(editmode);
            values.add(value);
            item.setText(entry.getKey());
            value.setText(String.valueOf(entry.getValue()));
            tr.addView(item);
            tr.addView(value);
            tbl.addView(tr);
            setupEditText(item,value,tr,this,tbl);
        }
        if(editmode){
            //add our empty one
            TableRow tr = new TableRow(this);
            EditText item = new EditText(this);
            items.add(item);
            item.setEnabled(editmode);
            EditText value = new EditText(this);
            value.setEnabled(editmode);
            values.add(value);
            tr.addView(item);
            tr.addView(value);
            tbl.addView(tr);
            setupEditText(item,value,tr,this,tbl);
        }
    }

    private void setupEditText(final EditText e, final EditText e2, final TableRow tr,
                               final Context context, final TableLayout tbl){
        e.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() == 0)                {
                    if(empty > 1){
                        items.remove(e);
                        values.remove(e2);
                        tr.removeView(e);
                        tr.removeView(e2);
                        empty--;
                    }
                }else{
                    if(empty < 1){
                        //add our empty one
                        TableRow tr = new TableRow(context);
                        EditText item = new EditText(context);
                        items.add(item);
                        item.setEnabled(true);
                        EditText value = new EditText(context);
                        value.setEnabled(true);
                        values.add(value);
                        tr.addView(item);
                        tr.addView(value);
                        tbl.addView(tr);
                        setupEditText(item,value,tr,context,tbl);
                        empty++;
                    }
                }
            }
        });
    }

}
