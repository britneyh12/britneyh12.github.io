package com.example.projecttwobh.ui.adjustments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        // Set up Add Adjustment button
        binding.buttonAdd.setOnClickListener(v -> {
            // Handle Add Adjustment logic
            String adjustmentDetails = binding.editTextAdjustmentDetails.getText().toString().trim();
        });

        // Set up Remove Adjustment button
        binding.buttonRemove.setOnClickListener(v -> {
            String adjustmentDetails = binding.editTextAdjustmentDetails.getText().toString().trim();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
