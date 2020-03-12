package solarapp.android.integral.com.solarapp.viewmodel;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import solarapp.android.integral.com.solarapp.LoginStatus;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginStatus> loginStatus = new MutableLiveData<LoginStatus>(LoginStatus.LOGIN_NOTHING);

    public LiveData<LoginStatus> getLoginStatus() {
        return loginStatus;
    }

    public void chekLogin(String email, String password) {

        loginStatus.postValue(LoginStatus.LOGIN_IN_PROGRESS);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                loginStatus.postValue(LoginStatus.LOGIN_SUCCESS);

            }
        }, 2000);


    }
}
