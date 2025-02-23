package com.example.projecttwobh.ui.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.projecttwobh.R;
import com.example.projecttwobh.databinding.FragmentDatabaseBinding;
import com.example.projecttwobh.ui.inventory.InventoryAdapter;
import com.example.projecttwobh.ui.inventory.InventoryItem;
import java.util.ArrayList;
import java.util.List;

public class DatabaseFragment extends Fragment {

    private FragmentDatabaseBinding binding;
    private DatabaseHelper dbHelper;
    private InventoryAdapter adapter;
    private List<InventoryItem> inventoryList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDatabaseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DatabaseHelper(requireContext());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new InventoryAdapter(new ArrayList<>());
        binding.recyclerView.setAdapter(adapter);
        loadInventoryData();


        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filterList(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filterList(newText);
                return true;
            }
        });

        // **Setup Sort Spinner**
        setupSortSpinner();
    }

    private void setupSortSpinner() {
        Spinner sortSpinner = binding.sortSpinner;
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.sort_options, // Defined in res/values/strings.xml
                android.R.layout.simple_spinner_item
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(spinnerAdapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    adapter.sortByName(); // **Sort alphabetically**
                } else if (position == 2) {
                    adapter.sortByQuantity(); // **Sort by quantity**
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void loadInventoryData() {
        inventoryList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.getAllItems();

        if (cursor.moveToFirst()) {
            do {
                String itemName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ITEM_NAME));
                String itemNumber = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ITEM_NUMBER));
                String location = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LOCATION));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_QUANTITY));

                inventoryList.add(new InventoryItem(itemName, itemNumber, location, quantity));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // Update adapter
        if (adapter != null) {
            adapter.setData(inventoryList);
        } else {
            adapter = new InventoryAdapter(inventoryList);
            binding.recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Prevent memory leaks
    }
}
