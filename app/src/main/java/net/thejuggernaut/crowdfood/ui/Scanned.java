package net.thejuggernaut.crowdfood.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import net.thejuggernaut.crowdfood.R;
import net.thejuggernaut.crowdfood.api.Point;
import net.thejuggernaut.crowdfood.api.Product;

import org.w3c.dom.Text;

import java.util.Map;

public class Scanned extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned);

        Intent intent = getIntent();
        Product p = (Product) intent.getSerializableExtra("PRODUCT");

        //TODO
        //Setup handle for when there is no data (the getTrust will fail)

        setupTitle(p.getProductName(),p.getTrust().get("ProductName"));
        setupWarnings();
        setupIngredients(p.getIngredients(), p.getTrust().get("Ingredients"));
        setupServing(p.getServing(),p.getTrust().get("Serving"));
        setupNutritional(p.getNutrition(), p.getTrust().get("Nutrition"));
    }


    private  void setupServing(String ra, Point point){
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.scanDisplay);

        LinearLayout servingLayout = new LinearLayout(this);
        servingLayout.setOrientation(LinearLayout.VERTICAL);

        //Title
        TextView title = new TextView(this);
        title.setText("Recommend serving: "+ra);
        servingLayout.addView(title);


        LinearLayout review = new LinearLayout(this);
        TextView upndown = new TextView(this);
        upndown.setText("Up: "+point.getConfirm()+" Down:"+point.getDeny());
        review.addView(upndown);
        Button editThis = new Button(this);
        editThis.setText("Edit");
        review.addView(editThis);


        servingLayout.addView(review);
        mainLayout.addView(servingLayout);

    }

    private void setupTitle(String name, Point point){
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.scanDisplay);

        LinearLayout productNameLayout = new LinearLayout(this);
        productNameLayout.setOrientation(LinearLayout.VERTICAL);

        //Title
        TextView title = new TextView(this);
        title.setTextAppearance(android.R.style.TextAppearance_DeviceDefault_DialogWindowTitle);
        title.setText("Product: "+name);
        productNameLayout.addView(title);


        LinearLayout review = new LinearLayout(this);
        TextView upndown = new TextView(this);
        upndown.setText("Up: "+point.getConfirm()+" Down:"+point.getDeny());
        review.addView(upndown);
        Button editThis = new Button(this);
        editThis.setText("Edit");
        review.addView(editThis);


        productNameLayout.addView(review);
        mainLayout.addView(productNameLayout);


    }

    private void setupWarnings(){
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.scanDisplay);

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


    private void setupIngredients(String[] ingredients, Point point){
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.scanDisplay);

        LinearLayout ingredientsLayout = new LinearLayout(this);
        ingredientsLayout.setOrientation(LinearLayout.VERTICAL);

        //Title
        TextView title = new TextView(this);
        title.setTextAppearance(android.R.style.TextAppearance_DeviceDefault_DialogWindowTitle);
        title.setText("Ingredients");
        ingredientsLayout.addView(title);

        //Ingredients list
        TextView text = new TextView(this);
        //Here we would bold any ing. that need warnings
        text.setText(TextUtils.join(", ",ingredients));

        ingredientsLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        ingredientsLayout.setPadding(0,10,0,10);
        ingredientsLayout.addView(text);


        LinearLayout review = new LinearLayout(this);
        TextView upndown = new TextView(this);
        upndown.setText("Up: "+point.getConfirm()+" Down:"+point.getDeny());
        review.addView(upndown);
        Button editThis = new Button(this);
        editThis.setText("Edit");
        review.addView(editThis);
        ingredientsLayout.addView(review);


        mainLayout.addView(ingredientsLayout);
    }



    private void setupNutritional(Map<String,Float> nutrition, Point point){
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.scanDisplay);

        LinearLayout nutritionLayout = new LinearLayout(this);
        nutritionLayout.setOrientation(LinearLayout.VERTICAL);

        //Title
        TextView title = new TextView(this);
        title.setTextAppearance(android.R.style.TextAppearance_DeviceDefault_DialogWindowTitle);
        title.setText("Nutrition");
        nutritionLayout.addView(title);

        //nutrition table
        TableLayout tbl = new TableLayout(this);
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
            TableRow tr = new TableRow(this);
            TextView item = new TextView(this);
            TextView value = new TextView(this);
            item.setText(entry.getKey());
            value.setText(String.valueOf(entry.getValue()));
            tr.addView(item);
            tr.addView(value);
            tbl.addView(tr);
        }



        nutritionLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        nutritionLayout.setPadding(0,10,0,10);
        nutritionLayout.addView(tbl);


        LinearLayout review = new LinearLayout(this);
        TextView upndown = new TextView(this);
        upndown.setText("Up: "+point.getConfirm()+" Down:"+point.getDeny());
        review.addView(upndown);
        Button editThis = new Button(this);
        editThis.setText("Edit");
        review.addView(editThis);
        nutritionLayout.addView(review);


        mainLayout.addView(nutritionLayout);
    }
}
