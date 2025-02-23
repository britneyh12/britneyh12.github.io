package com.example.projecttwobh;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.projecttwobh.ui.home.HomeFragment;
import com.example.projecttwobh.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Retrieve username passed from MainActivity
        String username = getIntent().getStringExtra("username");
        if (username != null) {
            // Example: Show a toast or update UI with the username
            Toast.makeText(this, "Welcome " + username, Toast.LENGTH_SHORT).show();
        }

        // If the fragment is not added already, add it
        if (savedInstanceState == null) {
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment_activity_home, homeFragment);
            transaction.commit();
        }

        // Set up bottom navigation (if applicable)
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_inventory, R.id.navigation_adjustments)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController); // Use navView from findViewById

        // Handle the button click to navigate to DatabaseActivity
        Button inventoryGridButton = findViewById(R.id.button_inventory_grid);
        inventoryGridButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, DatabaseActivity.class);
            startActivity(intent);
        });
    }
}
