package solarapp.android.integral.com.solarapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserRepository {

    public static UserRepository userRepository;

    public UserRepository getInstance() {

        if (userRepository == null) {

            userRepository = new UserRepository();
        }

        return userRepository;

    }

    public LiveData<String> getUserDetails() {

        MutableLiveData<String> data = new MutableLiveData<>();
        data.postValue("Basil Kurian Varghese");
        return (LiveData<String>) data;

    }

}
