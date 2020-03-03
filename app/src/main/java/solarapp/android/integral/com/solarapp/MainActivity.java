package solarapp.android.integral.com.solarapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import im.delight.android.webview.AdvancedWebView;
import solarapp.android.integral.com.solarapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements im.delight.android.webview.AdvancedWebView.Listener {

    private static final int FILE_CHOOSER_RESULT_CODE = 101;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.wvMain.setListener(this,this);
        binding.wvMain.addPermittedHostname(CommonUtils.APP_HOST);
        binding.wvMain.loadUrl(CommonUtils.APP_HOME);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        binding.wvMain.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        binding.wvMain.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.wvMain.onResume();
    }

    @Override
    protected void onDestroy() {
        binding.wvMain.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        if (!binding.wvMain.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    public void onPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(String url) {

    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

       /* Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
        startActivity(intent);*/

        if (AdvancedWebView.Browsers.hasAlternative(this)) {
            AdvancedWebView.Browsers.openUrl(this, url);
        }
    }


}
