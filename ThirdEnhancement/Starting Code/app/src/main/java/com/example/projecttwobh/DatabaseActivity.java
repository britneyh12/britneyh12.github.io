package com.example.projecttwobh;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.projecttwobh.databinding.FragmentDatabaseBinding;

public class DatabaseActivity extends Fragment {

    private FragmentDatabaseBinding binding;
    private com.example.projecttwobh.DatabaseHelper dbHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDatabaseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new com.example.projecttwobh.DatabaseHelper(getActivity());
        displayInventory();

        // Set up back button click listener
        binding.backButtonDatabase.setOnClickListener(v -> {
            if (getActivity() != null) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("Range")
    private void displayInventory() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(com.example.projecttwobh.DatabaseHelper.TABLE_INVENTORY, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            StringBuilder inventoryData = new StringBuilder();
            do {
                @SuppressLint("Range") String itemName = cursor.getString(cursor.getColumnIndex(com.example.projecttwobh.DatabaseHelper.COLUMN_ITEM_NAME));
                String itemNumber = cursor.getString(cursor.getColumnIndex(com.example.projecttwobh.DatabaseHelper.COLUMN_ITEM_NUMBER));
                String location = cursor.getString(cursor.getColumnIndex(com.example.projecttwobh.DatabaseHelper.COLUMN_LOCATION));
                int quantity = cursor.getInt(cursor.getColumnIndex(com.example.projecttwobh.DatabaseHelper.COLUMN_QUANTITY));

                inventoryData.append("Item: ").append(itemName).append("\n")
                        .append("Number: ").append(itemNumber).append("\n")
                        .append("Location: ").append(location).append("\n")
                        .append("Quantity: ").append(quantity).append("\n\n");
            } while (cursor.moveToNext());

            TextView inventoryTextView = binding.inventoryTextView;
            inventoryTextView.setText(inventoryData.toString());
        }

        cursor.close();
        db.close();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
