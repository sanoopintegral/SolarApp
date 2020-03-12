package solarapp.android.integral.com.solarapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import solarapp.android.integral.com.solarapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements AdvancedWebView.Listener {

    private static final int FILE_CHOOSER_RESULT_CODE = 101;
    private ActivityMainBinding binding;
    private File savingfile = null;
    private String failingurl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.wvMain.setListener(this, this);
        binding.wvMain.addPermittedHostname(CommonUtils.APP_HOST);
        binding.wvMain.loadUrl(CommonUtils.APP_HOME);
    }

    @Override
    protected void onStart() {
        super.onStart();
        listeners();
    }

    private void listeners() {

        binding.btnRetry.setOnClickListener(v -> {

            binding.wvMain.loadUrl(failingurl);

        });

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


        binding.btnRetry.setVisibility(View.GONE);

    }

    @Override
    public void onPageFinished(String url) {

        binding.wvMain.setVisibility(View.VISIBLE);

    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {

        Toast.makeText(this, R.string.network_error, Toast.LENGTH_SHORT).show();
        binding.wvMain.setVisibility(View.GONE);
        binding.btnRetry.setVisibility(View.VISIBLE);
        this.failingurl = failingUrl;

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

    public File createFileToSave() {

        File picturedirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        String name = null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
        name = simpleDateFormat.format(new Date());


        savingfile = new File(picturedirectory, "JPEG_" + name + ".jpg");
        try {
            savingfile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return savingfile;

    }


}
