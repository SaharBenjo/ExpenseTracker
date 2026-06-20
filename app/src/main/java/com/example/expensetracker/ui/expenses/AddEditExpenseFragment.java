package com.example.expensetracker.ui.expenses;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.expensetracker.data.entities.Expense;
import com.example.expensetracker.databinding.FragmentAddEditExpenseBinding;
import com.example.expensetracker.utils.PrefManager;
import com.example.expensetracker.viewmodel.ExpenseViewModel;

import java.util.Date;

public class AddEditExpenseFragment extends Fragment {

    private FragmentAddEditExpenseBinding binding;
    private ExpenseViewModel expenseViewModel;
    private PrefManager prefManager;
    private Bitmap capturedImage;

    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    capturedImage = (Bitmap) result.getData().getExtras().get("data");
                    binding.ivReceipt.setImageBitmap(capturedImage);
                    binding.ivReceipt.setVisibility(View.VISIBLE);
                }
            }
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddEditExpenseBinding.inflate(inflater, container, false);
        expenseViewModel = new ViewModelProvider(this).get(ExpenseViewModel.class);
        prefManager = new PrefManager(requireContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnCaptureImage.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraLauncher.launch(takePictureIntent);
        });

        binding.btnSave.setOnClickListener(v -> {
            String title = binding.etTitle.getText().toString().trim();
            String amountStr = binding.etAmount.getText().toString().trim();

            if (title.isEmpty() || amountStr.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount = Double.parseDouble(amountStr);
            String userId = prefManager.getUserId();

            // Note: In a real app, we would save the bitmap to a file and store the path.
            // For this project, we'll store null or a dummy path to keep it simple as per requirements.
            Expense expense = new Expense(title, amount, new Date().getTime(), "General", userId, "");
            expenseViewModel.insert(expense);

            Toast.makeText(getContext(), "Expense saved!", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(v).popBackStack();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}