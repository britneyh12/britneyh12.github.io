package com.example.projecttwobh;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.projecttwobh.databinding.FragmentAdjustmentsBinding;

public class AdjustmentsFragment extends Fragment {

    private FragmentAdjustmentsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdjustmentsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up back button click listener
        binding.backButtonAdjustments.setOnClickListener(v -> {
            if (getActivity() != null) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });

        // Set up Add Adjustment button
        Button buttonAdd = binding.buttonAdd;
        buttonAdd.setOnClickListener(v -> {
            // Handle Add Adjustment logic
            String adjustmentDetails = binding.editTextAdjustmentDetails.getText().toString().trim();
            // Implement your logic for adding adjustments here
        });

        // Set up Remove Adjustment button
        Button buttonRemove = binding.buttonRemove;
        buttonRemove.setOnClickListener(v -> {
            // Handle Remove Adjustment logic
            String adjustmentDetails = binding.editTextAdjustmentDetails.getText().toString().trim();
            // Implement your logic for removing adjustments here
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
