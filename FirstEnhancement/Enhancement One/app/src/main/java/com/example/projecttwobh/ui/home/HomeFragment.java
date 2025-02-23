package com.example.projecttwobh.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;  // Use ImageButton if that's what's in your XML
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.projecttwobh.SettingsActivity;
import com.example.projecttwobh.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;  // For view binding
    private ImageButton settingsButton;  // If you use a Button or ImageButton, make sure this matches the XML type

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout using binding
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Find the Settings button in the fragment's layout
        settingsButton = binding.settings;  // Use the correct ID here (Button or ImageButton)

        // Set an onClickListener to open SettingsActivity when the button is clicked
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to start SettingsActivity
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);  // Start the settings activity
            }
        });

        // Retrieve the username passed from HomeActivity
        String username = getActivity().getIntent().getStringExtra("username");

        // Check if username is not null and update TextView
        if (username != null) {
            TextView greetingText = binding.textView; // Ensure this ID exists in your XML layout
            greetingText.setText("Welcome to your Inventory Warehouse Application, " + username);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Clean up the binding to avoid memory leaks
    }
}
