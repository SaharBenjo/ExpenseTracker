package com.example.expensetracker.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.expensetracker.data.dao.ExpenseDao;
import com.example.expensetracker.data.dao.UserDao;
import com.example.expensetracker.data.database.AppDatabase;
import com.example.expensetracker.data.entities.Expense;
import com.example.expensetracker.data.entities.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppRepository {
    private final UserDao userDao;
    private final ExpenseDao expenseDao;
    private final ExecutorService executorService;

    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        userDao = db.userDao();
        expenseDao = db.expenseDao();
        executorService = Executors.newFixedThreadPool(4);
    }

    // User operations
    public void insertUser(User user) {
        executorService.execute(() -> userDao.insert(user));
    }

    public void updateUser(User user) {
        executorService.execute(() -> userDao.update(user));
    }

    public LiveData<User> getUserById(String userId) {
        return userDao.getUserById(userId);
    }

    // Expense operations
    public void insertExpense(Expense expense) {
        executorService.execute(() -> expenseDao.insert(expense));
    }

    public void updateExpense(Expense expense) {
        executorService.execute(() -> expenseDao.update(expense));
    }

    public void deleteExpense(Expense expense) {
        executorService.execute(() -> expenseDao.delete(expense));
    }

    public LiveData<List<Expense>> getExpensesForUser(String userId) {
        return expenseDao.getExpensesForUser(userId);
    }
}