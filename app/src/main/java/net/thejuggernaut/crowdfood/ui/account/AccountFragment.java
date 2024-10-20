package net.thejuggernaut.crowdfood.ui.account;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.thejuggernaut.crowdfood.R;
import net.thejuggernaut.crowdfood.accountApi.AccountApi;
import net.thejuggernaut.crowdfood.accountApi.Info;
import net.thejuggernaut.crowdfood.accountApi.Points;
import net.thejuggernaut.crowdfood.accountApi.SetupRetro;
import net.thejuggernaut.crowdfood.ui.scan.TableTextWatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;
    private boolean editMode = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel =
                ViewModelProviders.of(this).get(AccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        displayValues(root);
        getPoints(root);
        return root;
    }


    private void getPoints(View v){
        AccountApi accountAPI = SetupRetro.getRetro();
        Call<Points> call = accountAPI.loadPoints();
        call.enqueue(new Callback<Points>() { @Override
        public void onResponse(Call<Points> call, Response<Points> response) {
            if(response.isSuccessful()) {
                String points = response.body().getScan()+" points";
                ((TextView) v.findViewById(R.id.points)).setText(points);

                ((TextView) v.findViewById(R.id.level)).setText(response.body().getTrust());

            } else {
                //not found
                Log.i("Account",response.message());
            }
        }


            @Override
            public void onFailure(Call<Points> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }


    private void displayValues(View v){
        TextView allergiesText = (TextView) v.findViewById(R.id.allergies);
        EditText allergiesEdit = (EditText) v.findViewById(R.id.allergiesEdit);
        SharedPreferences pref= v.getContext().getSharedPreferences("AccountInfo",MODE_PRIVATE);
        String allergies = pref.getString("Allergies","");
        allergiesText.setText(allergies);
        allergiesEdit.setText(allergies);
        if (editMode){
            allergiesEdit.setVisibility(View.VISIBLE);
            allergiesText.setVisibility(View.GONE);
        }else{
            allergiesEdit.setVisibility(View.GONE);
            allergiesText.setVisibility(View.VISIBLE);
        }

        displayRecommended(v);

    }


    ArrayList<EditText> items;
    ArrayList<EditText> values;
    private void displayRecommended(View v) {

        items = new ArrayList<>();
        values = new ArrayList<>();

        //get from shared prefs
        SharedPreferences pref= v.getContext().getSharedPreferences("AccountInfo",MODE_PRIVATE);
        String storedHashMapString = pref.getString("Recommended", "");
        java.lang.reflect.Type type = new TypeToken<HashMap<String, Float>>(){}.getType();
        Gson gson = new Gson();

        HashMap<String, Float> map = gson.fromJson(storedHashMapString, type);

        TableLayout tbl = (TableLayout) v.findViewById(R.id.recommendedTable);
        tbl.removeAllViews();

        if (map == null) {
            HashMap<String, Float> m = new HashMap<>();
            map = m;
        }
        for (Map.Entry<String, Float> entry : map.entrySet()) {
            TableRow curRow = new TableRow(v.getContext());
            EditText ent2 = new EditText(v.getContext());
            if (!editMode) {
                TextView ent = new TextView(v.getContext());
                ent.setText(entry.getKey());
                curRow.addView(ent);
            } else {
                ent2.setText(entry.getKey());
                curRow.addView(ent2);
                items.add(ent2);
            }


            EditText val = new EditText(v.getContext());


            if (!editMode) {
                val.setEnabled(false);

            }
            values.add(val);


            val.setText(String.valueOf(entry.getValue()));


            curRow.addView(val);

            TableTextWatch tbc = new TableTextWatch(items, values, curRow, ent2, val, v.getContext(), tbl);
            val.addTextChangedListener(tbc);
            ent2.addTextChangedListener(tbc);


            tbl.addView(curRow);

        }

        //Add our empty row
        if (editMode) {
            TableRow curRow = new TableRow(v.getContext());
            EditText ent = new EditText(v.getContext());
            curRow.addView(ent);
            items.add(ent);
            EditText val = new EditText(v.getContext());
            values.add(val);
            ent.setHint("Fat");
            val.setHint("100");
            curRow.addView(val);
            tbl.addView(curRow);
            TableTextWatch tbc = new TableTextWatch(items, values, curRow, ent, val, v.getContext(), tbl);
            val.addTextChangedListener(tbc);
            ent.addTextChangedListener(tbc);
        }



    }


}