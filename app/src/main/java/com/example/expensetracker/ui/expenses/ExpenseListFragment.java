package com.example.expensetracker.ui.expenses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.expensetracker.R;
import com.example.expensetracker.databinding.FragmentExpenseListBinding;

public class ExpenseListFragment extends Fragment {

    private FragmentExpenseListBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentExpenseListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.fabAddExpense.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_expenseListFragment_to_addEditExpenseFragment);
        });

        // Set up RecyclerView here
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}