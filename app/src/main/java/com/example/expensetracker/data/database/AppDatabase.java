package com.example.expensetracker.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.expensetracker.data.dao.ExpenseDao;
import com.example.expensetracker.data.dao.UserDao;
import com.example.expensetracker.data.entities.Expense;
import com.example.expensetracker.data.entities.User;

@Database(entities = {User.class, Expense.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract ExpenseDao expenseDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "expense_tracker_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}