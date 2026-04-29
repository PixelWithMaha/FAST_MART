package com.example.assignment3.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.assignment3.data.model.User;
import com.example.assignment3.data.repository.UserRepository;

public class AuthViewModel extends ViewModel {
    private UserRepository repository = new UserRepository();
    public MutableLiveData<User> userLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public void signUp(User user, String password) {
        repository.registerUser(user, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) userLiveData.setValue(user);
            else errorLiveData.setValue(task.getException().getMessage());
        });
    }

    public void login(String email, String password) {
        repository.loginUser(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) userLiveData.setValue(task.getResult().getValue(User.class));
            else errorLiveData.setValue(task.getException().getMessage());
        });
    }
}