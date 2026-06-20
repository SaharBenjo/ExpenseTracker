package com.example.expensetracker.ui.auth;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.expensetracker.data.entities.User;
import com.example.expensetracker.databinding.FragmentRegisterBinding;
import com.example.expensetracker.viewmodel.UserViewModel;

import java.util.Calendar;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private UserViewModel userViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Date Picker for Birth Date
        binding.etBirthDate.setOnClickListener(v -> showDatePicker());

        binding.btnRegister.setOnClickListener(v -> {
            String id = binding.etId.getText().toString().trim();
            String fullName = binding.etFullName.getText().toString().trim();
            String email = binding.etEmail.getText().toString().trim();
            String phone = binding.etPhone.getText().toString().trim();
            String birthDate = binding.etBirthDate.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            if (id.isEmpty() || fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            User newUser = new User(id, fullName, email, phone, birthDate, password);
            userViewModel.insert(newUser);

            Toast.makeText(getContext(), "Registration successful!", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(v).popBackStack();
        });
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year1, monthOfYear, dayOfMonth) -> 
                        binding.etBirthDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1),
                year, month, day);
        datePickerDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}