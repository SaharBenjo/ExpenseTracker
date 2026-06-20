package com.example.expensetracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.expensetracker.data.entities.User;
import com.example.expensetracker.data.repository.AppRepository;

public class UserViewModel extends AndroidViewModel {
    private final AppRepository repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
    }

    public void insert(User user) {
        repository.insertUser(user);
    }

    public void update(User user) {
        repository.updateUser(user);
    }

    public LiveData<User> getUserById(String userId) {
        return repository.getUserById(userId);
    }
    
    // Simple authentication helper
    public void login(String email, String password, LoginCallback callback) {
        // Since database operations must be off the main thread
        new Thread(() -> {
            com.example.expensetracker.data.database.AppDatabase db = 
                com.example.expensetracker.data.database.AppDatabase.getInstance(getApplication());
            User user = db.userDao().login(email, password);
            callback.onResult(user);
        }).start();
    }

    public interface LoginCallback {
        void onResult(User user);
    }
}