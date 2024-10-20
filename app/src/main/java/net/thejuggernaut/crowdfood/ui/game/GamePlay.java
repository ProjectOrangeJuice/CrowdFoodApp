package net.thejuggernaut.crowdfood.ui.game;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.thejuggernaut.crowdfood.R;
import net.thejuggernaut.crowdfood.api.FoodieAPI;
import net.thejuggernaut.crowdfood.api.Product;
import net.thejuggernaut.crowdfood.api.SetupRetro;
import net.thejuggernaut.crowdfood.gameApi.Game;
import net.thejuggernaut.crowdfood.gameApi.GameApi;
import net.thejuggernaut.crowdfood.gameApi.Question;
import net.thejuggernaut.crowdfood.gameApi.SetupGame;
import net.thejuggernaut.crowdfood.ui.GameMode;
import net.thejuggernaut.crowdfood.ui.scan.DisplayProduct;
import net.thejuggernaut.crowdfood.ui.scan.ScanFragment;

import org.w3c.dom.Text;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GamePlay extends Fragment  implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        mScannerView = new ZBarScannerView(getActivity());

        return mScannerView;
    }

    @Override
    public void onResume() {
        super.onResume();

        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(getActivity(), "Contents = " + rawResult.getContents() +
                ", Format = " + rawResult.getBarcodeFormat().getName(), Toast.LENGTH_SHORT).show();
        scanItem(rawResult.getContents());
        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(GamePlay.this);
            }
        }, 2000);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }



    private  void scanItem(String barcode){
        FoodieAPI foodieAPI = SetupRetro.getRetro(getContext());
        Call<Product> call = foodieAPI.loadProduct(barcode);
        call.enqueue(new Callback<Product>() { @Override
        public void onResponse(Call<Product> call, Response<Product> response) {
            if(response.isSuccessful()) {





            } else {
                //not found
                Log.i("PRODUCT21",response.message());

                //Ask if they want to create it
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:

                                Product p = new Product();//All empty
                                p.setID(barcode);
                                Intent intent = new Intent(getView().getContext(), DisplayProduct.class);
                                intent.putExtra("PRODUCT",p);
                                startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Not found").setMessage("Create it?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        }


            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }

}
