package com.example.projecttwobh.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.appcompat.app.AppCompatDelegate;
import com.example.projecttwobh.R;
import com.example.projecttwobh.databinding.FragmentSettingsBinding;
import com.example.projecttwobh.ui.database.DatabaseFragment;
import com.example.projecttwobh.ui.database.DatabaseHelper;

public class SettingsFragment extends Fragment {

    private static final String PREFS_NAME = "AppPreferences";
    private static final String DARK_MODE_KEY = "isDarkMode";
    private static final String TRAINING_MODE_KEY = "training_mode_enabled";

    private FragmentSettingsBinding binding;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, requireContext().MODE_PRIVATE);

        // Load and Set Theme Switch State
        boolean isDarkMode = sharedPreferences.getBoolean(DARK_MODE_KEY, false);
        binding.themeSwitch.setChecked(isDarkMode);

        binding.themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            savePreference(DARK_MODE_KEY, isChecked);
        });

        // Load and Set Training Mode Switch
        boolean isTrainingModeEnabled = sharedPreferences.getBoolean(TRAINING_MODE_KEY, false);
        binding.trainingSwitch.setChecked(isTrainingModeEnabled);

        binding.trainingSwitch.setOnCheckedChangeListener(this::onTrainingModeToggled);

        // Handle Back Button Navigation
        binding.backButtonDatabase2.setOnClickListener(v -> Navigation.findNavController(view).navigateUp());
    }

    private void savePreference(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private void onTrainingModeToggled(CompoundButton buttonView, boolean isChecked) {
        savePreference(TRAINING_MODE_KEY, isChecked);
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());

        if (isChecked) {
            dbHelper.insertFakeData();  // Add preloaded data when Training Mode is ON
        } else {
            dbHelper.clearInventory();  // Remove preloaded data when Training Mode is OFF
        }

        //  Use Navigation Component to properly refresh the Inventory Screen
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(R.id.inventoryFragment);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
