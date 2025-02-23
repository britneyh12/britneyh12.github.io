package com.example.projecttwobh.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.projecttwobh.R;
import com.example.projecttwobh.ui.settings.SettingsFragment;
import com.example.projecttwobh.databinding.FragmentHomeBinding;
import com.example.projecttwobh.ui.database.DatabaseFragment;
import com.example.projecttwobh.ui.adjustments.AdjustmentsFragment;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Show BottomNavigationView when entering HomeFragment
        requireActivity().findViewById(R.id.nav_view).setVisibility(View.VISIBLE);

        // Retrieve username passed from LoginFragment
        Bundle args = getArguments();
        if (args != null && args.containsKey("username")) {
            String username = args.getString("username");
            binding.textView.setText("Welcome to your Inventory Warehouse Application, " + username);
        }

        // Navigate to Inventory Fragment when button is clicked
        binding.buttonInventoryGrid.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_inventoryFragment)
        );

        // Navigate to Adjustments Fragment when button is clicked
        binding.buttonInventoryAdjustments.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_adjustmentsFragment)
        );

        // Open SettingsFragment when settings button is clicked
        binding.settings.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.settingsFragment);
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
