package net.thejuggernaut.crowdfood;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.authentication.storage.SecureCredentialsManager;
import com.auth0.android.authentication.storage.SharedPreferencesStorage;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import net.thejuggernaut.crowdfood.accountApi.AccountApi;
import net.thejuggernaut.crowdfood.accountApi.Info;
import net.thejuggernaut.crowdfood.accountApi.SetupRetro;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Auth0 auth0;
    public static final String BASE_URL = "http://192.168.1.102:8900";
    private Class<?> mClss;
    private static final int ZBAR_CAMERA_PERMISSION = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        launchActivity(this.getClass());

        profileRefresh();
    }
    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZBAR_CAMERA_PERMISSION);
        } else {

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZBAR_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(mClss != null) {
                        Intent intent = new Intent(this, mClss);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(this, "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
    private void profileRefresh(){
        AccountApi accountAPI = SetupRetro.getRetro(this);
        Call<Info> call = accountAPI.loadAccount();
        call.enqueue(new Callback<Info>() { @Override
        public void onResponse(Call<Info> call, Response<Info> response) {
            if(response.isSuccessful()) {

                //Store allergies
                SharedPreferences pref = getSharedPreferences("AccountInfo",MODE_PRIVATE);
                String allergies = "";
                if(response.body().getAllergies() != null){
                    allergies = TextUtils.join(",", response.body().getAllergies());
                }
                pref.edit().putString("Allergies",allergies).apply();

                //Convert the recommended back to json!
                Gson gson = new Gson();
                String mapString =gson.toJson (response.body().getRecommendedNutrition());
                System.out.println("The map looks like this --- "+mapString);

                pref.edit().putString("Recommended",mapString).apply();

            } else {
                //not found
                Log.i("Account",response.message());
            }
        }


            @Override
            public void onFailure(Call<Info> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }

}
