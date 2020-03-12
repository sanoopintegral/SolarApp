package solarapp.android.integral.com.solarapp;

import android.os.Bundle;
import android.webkit.WebSettings;

import androidx.appcompat.app.AppCompatActivity;

import solarapp.android.integral.com.solarapp.databinding.ActivityJavaScriptInterfaceBinding;

public class JavaScriptInterfaceActivity extends AppCompatActivity {

    ActivityJavaScriptInterfaceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJavaScriptInterfaceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        WebSettings settings = binding.wbMain.getSettings();
        settings.setJavaScriptEnabled(true);
        binding.wbMain.addJavascriptInterface(new WebAppInterface(this), "AndroidInterface");
        binding.wbMain.loadUrl("file:///android_asset/test.html");
    }
}
