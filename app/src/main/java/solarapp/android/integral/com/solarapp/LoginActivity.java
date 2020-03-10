package solarapp.android.integral.com.solarapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import solarapp.android.integral.com.solarapp.databinding.ActivityLoginBinding;
import solarapp.android.integral.com.solarapp.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding activityLoginBinding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginViewModel.getName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                activityLoginBinding.edtName.setText(s);

            }
        });

        activityLoginBinding.btnLogin.setOnClickListener(v -> {

            loginViewModel.changeValue("Eldhose p varghese");
        });
    }
}
