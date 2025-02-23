package com.example.projecttwobh.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.example.projecttwobh.ui.database.DatabaseHelper;
import com.example.projecttwobh.R;
import com.example.projecttwobh.databinding.FragmentAuthenticationBinding;

public class LoginFragment extends Fragment {

    private FragmentAuthenticationBinding binding;
    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAuthenticationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseHelper = new DatabaseHelper(requireContext());

        // Hide BottomNavigationView when on login screen
        requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);

        // Register Button Click
        binding.button.setOnClickListener(v -> {
            String username = binding.editTextText3.getText().toString().trim();
            String password = binding.editTextTextPassword2.getText().toString().trim();

            if (!username.isEmpty() && !password.isEmpty()) {
                boolean isInserted = databaseHelper.addUser(username, password);
                if (isInserted) {
                    Toast.makeText(requireContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Please enter username and password", Toast.LENGTH_SHORT).show();
            }
        });

        // Sign-in Button Click
        binding.button2.setOnClickListener(v -> {
            String username = binding.editTextText3.getText().toString().trim();
            String password = binding.editTextTextPassword2.getText().toString().trim();

            if (!username.isEmpty() && !password.isEmpty()) {
                boolean userExists = databaseHelper.checkUser(username, password);
                if (userExists) {
                    Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show();

                    // Navigate to HomeFragment and show the bottom nav again
                    requireActivity().findViewById(R.id.nav_view).setVisibility(View.VISIBLE);
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment, bundle);
                } else {
                    Toast.makeText(requireContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Please enter username and password", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Prevent memory leaks
    }
}
