package com.example.expensetracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.expensetracker.data.entities.Expense;
import com.example.expensetracker.data.repository.AppRepository;

import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {
    private final AppRepository repository;

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
    }

    public void insert(Expense expense) {
        repository.insertExpense(expense);
    }

    public void update(Expense expense) {
        repository.updateExpense(expense);
    }

    public void delete(Expense expense) {
        repository.deleteExpense(expense);
    }

    public LiveData<List<Expense>> getExpensesForUser(String userId) {
        return repository.getExpensesForUser(userId);
    }
}