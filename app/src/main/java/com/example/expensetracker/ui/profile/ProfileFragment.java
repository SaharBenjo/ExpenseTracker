package com.example.expensetracker.ui.profile;

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
import com.example.expensetracker.data.entities.User;
import com.example.expensetracker.databinding.FragmentProfileBinding;
import com.example.expensetracker.utils.PrefManager;
import com.example.expensetracker.viewmodel.UserViewModel;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private UserViewModel userViewModel;
    private PrefManager prefManager;
    private User currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        prefManager = new PrefManager(requireContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String userId = prefManager.getUserId();
        if (userId != null) {
            userViewModel.getUserById(userId).observe(getViewLifecycleOwner(), user -> {
                if (user != null) {
                    currentUser = user;
                    binding.etFullName.setText(user.getFullName());
                    binding.etEmail.setText(user.getEmail());
                    binding.etPhone.setText(user.getPhone());
                }
            });
        }

        binding.btnUpdate.setOnClickListener(v -> {
            if (currentUser != null) {
                currentUser.setFullName(binding.etFullName.getText().toString().trim());
                currentUser.setEmail(binding.etEmail.getText().toString().trim());
                currentUser.setPhone(binding.etPhone.getText().toString().trim());

                userViewModel.update(currentUser);
                Toast.makeText(getContext(), "Profile updated!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnLogout.setOnClickListener(v -> {
            prefManager.logout();
            // Navigate back to login and clear the backstack
            Navigation.findNavController(v).navigate(R.id.loginFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}