package solarapp.android.integral.com.solarapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

        loginViewModel.getLoginStatus().observe(this, new Observer<LoginStatus>() {
            @Override
            public void onChanged(LoginStatus loginStatus) {

                if (loginStatus == LoginStatus.LOGIN_NOTHING) {

                    activityLoginBinding.llytLoginProgress.setVisibility(View.INVISIBLE);
                    activityLoginBinding.btnLogin.setVisibility(View.VISIBLE);

                } else if (loginStatus == LoginStatus.LOGIN_FAILED) {

                    activityLoginBinding.llytLoginProgress.setVisibility(View.INVISIBLE);
                    activityLoginBinding.btnLogin.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show();

                } else if (loginStatus == LoginStatus.LOGIN_SUCCESS) {

                    activityLoginBinding.llytLoginProgress.setVisibility(View.INVISIBLE);
                    activityLoginBinding.btnLogin.setVisibility(View.VISIBLE);
                    openDashboard();

                } else if (loginStatus == LoginStatus.LOGIN_IN_PROGRESS) {

                    activityLoginBinding.llytLoginProgress.setVisibility(View.VISIBLE);
                    activityLoginBinding.btnLogin.setVisibility(View.INVISIBLE);

                }

            }
        });

        activityLoginBinding.btnLogin.setOnClickListener(v -> {

            String email = activityLoginBinding.edtEmail.getText().toString();
            String password = activityLoginBinding.edtPassword.getText().toString();
            loginViewModel.chekLogin(email, password);

        });

        activityLoginBinding.btnRegister.setOnClickListener(v -> {

            openRegisterPage();

        });
    }

    private void openRegisterPage() {

        Intent registerintent = new Intent(this, RegisterActivity.class);
        startActivity(registerintent);
    }

    private void openDashboard() {

        Intent dahsboardintent = new Intent(this, DashboardActivity.class);
        dahsboardintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(dahsboardintent);

    }


}
