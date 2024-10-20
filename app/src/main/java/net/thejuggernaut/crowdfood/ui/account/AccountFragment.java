package net.thejuggernaut.crowdfood.ui.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.auth0.android.Auth0;
import com.auth0.android.Auth0Exception;
import com.auth0.android.provider.VoidCallback;
import com.auth0.android.provider.WebAuthProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.thejuggernaut.crowdfood.MainActivity;
import net.thejuggernaut.crowdfood.MainAuth;
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
import static android.content.Context.POWER_SERVICE;

public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;
    private boolean editMode = false;
    View v;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel =
                ViewModelProviders.of(this).get(AccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        displayValues(root);
        getPoints(root);

        //Enable edit button
        FloatingActionButton btn = (FloatingActionButton) root.findViewById(R.id.accountEdit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              editMode();
            }
        });

        Button lg = (Button) root.findViewById(R.id.logoutBut);
        lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });


        v = root;
        return root;
    }


    private void logout(){
        Auth0 auth0 = new Auth0(v.getContext());
        WebAuthProvider.logout(auth0)
                .withScheme("demo")
                .start(v.getContext(), new VoidCallback() {
                    @Override
                    public void onSuccess(Void payload) {
                        SharedPreferences pref = v.getContext().getSharedPreferences("AccountInfo",MODE_PRIVATE);
                        pref.edit().putString("Token","").apply();

                        Intent intent = new Intent(v.getContext(), MainAuth.class);
                        startActivity(intent);
                       getActivity().finish();
                    }

                    @Override
                    public void onFailure(Auth0Exception error) {
                        Toast.makeText(v.getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void editMode() {
        if (editMode) {
           save();
        }
        editMode = !editMode;
        displayValues(v);
    }
    private void getPoints(View v){
        AccountApi accountAPI = SetupRetro.getRetro(getContext());
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




    private boolean checkTable(Map<String, Float> newmap) {
        //get from shared prefs
        SharedPreferences pref= v.getContext().getSharedPreferences("AccountInfo",MODE_PRIVATE);
        String storedHashMapString = pref.getString("Recommended", "");
        java.lang.reflect.Type type = new TypeToken<HashMap<String, Float>>(){}.getType();
        Gson gson = new Gson();

        HashMap<String, Float> map = gson.fromJson(storedHashMapString, type);


        System.out.println("Size.. " + newmap.size());
        if (newmap.size() != map.size()) {
            System.out.println("Diff size for table");
            return true;
        }

        for (Map.Entry<String, Float> entry :map.entrySet()) {
            if (!newmap.containsKey(entry.getKey())) {
                System.out.println("I can't find a value so returning true");
                return true;
            }
            boolean found = false;
            for (Map.Entry<String, Float> nentry : newmap.entrySet()) {
                if (nentry.getKey() == entry.getKey() &&
                        nentry.getValue() == entry.getValue()) {
                    found = true;
                }

            }
            if (!found) {
                System.out.println("I can't find a float so returning true");
                return true;
            }
            map.remove(entry.getKey());

        }
        return false;
    }


    private boolean checkIng(String[] ing) {
        SharedPreferences pref= v.getContext().getSharedPreferences("AccountInfo",MODE_PRIVATE);
        String[] old =  pref.getString("Allergies","").split(",");
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

        EditText inge = (EditText) v.findViewById(R.id.allergiesEdit);
        Boolean changed = false;
        SharedPreferences pref = v.getContext().getSharedPreferences("AccountInfo",MODE_PRIVATE);
        pref.edit().putString("Allergies",inge.getText().toString()).apply();


        Info info = new Info();
        info.setAllergies(inge.getText().toString().split(","));

        //Get the new map of values
        Map<String, Float> n = new HashMap<>();
        for (int i = 0; i < items.size() - 1; i++) {

            if (items.get(i).getText().toString().equals("")
                    && values.get(i).getText().toString().equals("")) {
                System.out.println("Empty value");
            } else {

                n.put(items.get(i).getText().toString(), Float.parseFloat(values.get(i).getText().toString()));
                System.out.println("Put! " + n.size());
            }
        }


        info.setRecommendedNutrition(n);

        //Convert the recommended back to json!
        Gson gson = new Gson();
        String mapString =gson.toJson (n);
        System.out.println("The map looks like this --- "+mapString);

        pref.edit().putString("Recommended",mapString).apply();

        System.out.println("CHecking.. " + n.size());
        if (checkTable(n)) {
            changed = true; //need to check to see if nutritional info has changed

        }
        if(checkIng(inge.getText().toString().split(","))){
            changed = true;
        }


        if (changed) {
            AccountApi accountApi = SetupRetro.getRetro(getContext());
            Call<Void> call = accountApi.updateAccount(info);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.i("UPDATE", "WORKED");
                    } else {
                        //not found
                        Log.i("UPDATE", response.message());
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