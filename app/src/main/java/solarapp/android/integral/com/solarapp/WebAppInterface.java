package solarapp.android.integral.com.solarapp;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class WebAppInterface {

    Context context;

    public WebAppInterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void showToast(String message){

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
