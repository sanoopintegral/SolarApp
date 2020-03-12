package solarapp.android.integral.com.solarapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import solarapp.android.integral.com.solarapp.databinding.ActivityDashboardBinding;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding activityDashboardBinding;
    private boolean doublepressedback = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDashboardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityDashboardBinding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        listeners();
    }

    private void listeners() {

        activityDashboardBinding.btnShowWebView.setOnClickListener(v -> {

            openHomeWebView();

        });

        activityDashboardBinding.btnCurrentLocation.setOnClickListener(v -> {

            openCurrentLocationPage();


        });
        activityDashboardBinding.btnJavascriptInterface.setOnClickListener(v -> {
            showJavaScriptWebpage();
        });
    }

    private void openCurrentLocationPage() {

        Intent currentlocationintent = new Intent(this, MyLocationActivity.class);
        startActivity(currentlocationintent);

    }

    private void openHomeWebView() {

        Intent homewebviewintent = new Intent(this, MainActivity.class);
        startActivity(homewebviewintent);
    }

    private void showJavaScriptWebpage() {

        Intent intent = new Intent(this, JavaScriptInterfaceActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        if (doublepressedback) {
            super.onBackPressed();
        } else {

            Toast.makeText(this, "Press back button again to exit", Toast.LENGTH_SHORT).show();
            doublepressedback = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    doublepressedback = false;
                }
            }, 2000);
        }
    }
}
