package net.thejuggernaut.crowdfood.ui.scan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import net.thejuggernaut.crowdfood.MainActivity;
import net.thejuggernaut.crowdfood.R;
import net.thejuggernaut.crowdfood.api.FoodieAPI;
import net.thejuggernaut.crowdfood.api.Product;
import net.thejuggernaut.crowdfood.api.SetupRetro;
import net.thejuggernaut.crowdfood.ui.CameraPreview;
import net.thejuggernaut.crowdfood.ui.Scanned;

import java.io.IOException;
import java.util.List;

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
        //should be done on another thread
        mCamera =  Camera.open();
        mCamera.setDisplayOrientation(90);
        cameraPreview = (LinearLayout) root.findViewById(R.id.la);
        mPreview = new CameraPreview(getContext(), mCamera);
        cameraPreview.addView(mPreview);
        mCamera.startPreview();
        mCamera = Camera.open(0);
        mCamera.setDisplayOrientation(90);
        mPicture = getPictureCallback();
        mPreview.refreshCamera(mCamera);
        return root;
    }
    private Camera.PictureCallback getPictureCallback() {
        Camera.PictureCallback picture = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                //Intent intent = new Intent(this,Cam.class);
                //startActivity(intent);
            }
        };
        return picture;
    }
    public void buttonActions(View view) {
        Button scanButton = (Button) view.findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                scanItem(v);
            }
        });

    }



    private Camera mCamera;
    private CameraPreview mPreview;
    private Camera.PictureCallback mPicture;
    private Button capture, switchCamera;
    private Context myContext;
    private LinearLayout cameraPreview;
    private boolean cameraFront = false;
    public static Bitmap bitmap;






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