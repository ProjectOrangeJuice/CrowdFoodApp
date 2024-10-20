package net.thejuggernaut.crowdfood.ui.scan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import net.thejuggernaut.crowdfood.R;
import net.thejuggernaut.crowdfood.api.FoodieAPI;
import net.thejuggernaut.crowdfood.api.Product;
import net.thejuggernaut.crowdfood.api.SetupRetro;
import net.thejuggernaut.crowdfood.ui.Scanned;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanFragment extends Fragment {

    private ScanViewModel scanViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        scanViewModel =
                ViewModelProviders.of(this).get(ScanViewModel.class);
        View root = inflater.inflate(R.layout.fragment_scan, container, false);
        buttonActions(root);
        return root;
    }

    public void buttonActions(View view) {
        Button scanButton = (Button) view.findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                scanItem(v);
            }
        });

    }

    public void scanItem(final View view){
        String barcode = "000343";
        FoodieAPI foodieAPI = SetupRetro.getRetro();
        Call<Product> call = foodieAPI.loadProduct(barcode);
        call.enqueue(new Callback<Product>() { @Override
        public void onResponse(Call<Product> call, Response<Product> response) {
            if(response.isSuccessful()) {

                //Display the results
                Intent intent = new Intent(view.getContext(), Scanned.class);
                intent.putExtra("PRODUCT",response.body());
                startActivity(intent);



            } else {
                //not found
                Log.i("PRODUCT",response.message());
            }
        }


            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }
}