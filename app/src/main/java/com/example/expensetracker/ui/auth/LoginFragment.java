package com.example.expensetracker.ui.auth;

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

import com.example.expensetracker.R;
import com.example.expensetracker.databinding.FragmentLoginBinding;
import com.example.expensetracker.utils.PrefManager;
import com.example.expensetracker.viewmodel.UserViewModel;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private UserViewModel userViewModel;
    private PrefManager prefManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        prefManager = new PrefManager(requireContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Check if user is already logged in
        if (prefManager.getUserId() != null) {
            navigateToMain(view);
        }

        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            userViewModel.login(email, password, user -> {
                // UI updates must happen on main thread
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        if (user != null) {
                            prefManager.setUserId(user.getId());
                            Toast.makeText(getContext(), "Welcome back, " + user.getFullName(), Toast.LENGTH_SHORT).show();
                            navigateToMain(v);
                        } else {
                            Toast.makeText(getContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        });

        binding.tvRegister.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment);
        });
    }

    private void navigateToMain(View view) {
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_expenseListFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}