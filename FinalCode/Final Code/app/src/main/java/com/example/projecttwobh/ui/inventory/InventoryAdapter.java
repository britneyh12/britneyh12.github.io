package com.example.projecttwobh.ui.inventory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projecttwobh.R;
import java.util.ArrayList;
import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder> {

    private List<InventoryItem> inventoryList;  // The list used for display
    private List<InventoryItem> fullList;       // The full original list for filtering

    public InventoryAdapter(List<InventoryItem> inventoryList) {
        this.inventoryList = new ArrayList<>(inventoryList);
        this.fullList = new ArrayList<>(inventoryList);
    }

    @NonNull
    @Override
    public InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inventory, parent, false);
        return new InventoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryViewHolder holder, int position) {
        InventoryItem item = inventoryList.get(position);
        holder.itemName.setText(item.getItemName());
        holder.itemNumber.setText(item.getItemNumber());
        holder.location.setText(item.getLocation());
        holder.quantity.setText(String.valueOf(item.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return inventoryList.size();
    }

    // Search Functionality
    public void filterList(String query) {
        List<InventoryItem> filteredList = new ArrayList<>();
        for (InventoryItem item : fullList) {  // Using fullList for filtering
            if (item.getItemName().toLowerCase().contains(query.toLowerCase()) ||
                    item.getItemNumber().toLowerCase().contains(query.toLowerCase()) ||
                    item.getLocation().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }
        inventoryList.clear();
        inventoryList.addAll(filteredList);
        notifyDataSetChanged();
    }

    // **Sorting Functionality**
    public void sortByName() {
        inventoryList.sort((item1, item2) -> item1.getItemName().compareToIgnoreCase(item2.getItemName()));
        notifyDataSetChanged();
    }

    public void sortByQuantity() {
        inventoryList.sort((item1, item2) -> Integer.compare(item2.getQuantity(), item1.getQuantity()));
        notifyDataSetChanged();
    }

    // Update Adapter Data
    public void setData(List<InventoryItem> newInventoryList) {
        this.inventoryList.clear();
        this.inventoryList.addAll(newInventoryList);

        this.fullList.clear(); // Ensure `fullList` gets updated correctly
        this.fullList.addAll(newInventoryList);

        notifyDataSetChanged();
    }

    static class InventoryViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemNumber, location, quantity;

        InventoryViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemNumber = itemView.findViewById(R.id.itemNumber);
            location = itemView.findViewById(R.id.location);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}
