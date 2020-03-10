package solarapp.android.integral.com.solarapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<String> mName = new MutableLiveData<>();

    public LiveData<String> getName() {

        return mName;
    }

    public void changeValue(String data) {


        mName.postValue(data);
    }
}
