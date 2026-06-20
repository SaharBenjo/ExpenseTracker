package com.example.expensetracker.ui.expenses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.expensetracker.R;
import com.example.expensetracker.databinding.FragmentExpenseListBinding;
import com.example.expensetracker.ui.adapters.ExpenseAdapter;
import com.example.expensetracker.utils.PrefManager;
import com.example.expensetracker.viewmodel.ExpenseViewModel;

public class ExpenseListFragment extends Fragment {

    private FragmentExpenseListBinding binding;
    private ExpenseViewModel expenseViewModel;
    private PrefManager prefManager;
    private ExpenseAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentExpenseListBinding.inflate(inflater, container, false);
        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        prefManager = new PrefManager(requireContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();

        binding.fabAddExpense.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_expenseListFragment_to_addEditExpenseFragment);
        });

        binding.btnProfile.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_expenseListFragment_to_profileFragment);
        });

        String userId = prefManager.getUserId();
        if (userId != null) {
            expenseViewModel.getExpensesForUser(userId).observe(getViewLifecycleOwner(), expenses -> {
                adapter.setExpenses(expenses);
            });
        }
    }

    private void setupRecyclerView() {
        adapter = new ExpenseAdapter();
        binding.rvExpenses.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvExpenses.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}