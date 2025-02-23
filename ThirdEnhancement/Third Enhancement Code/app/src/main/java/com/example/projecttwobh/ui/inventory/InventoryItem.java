package com.example.projecttwobh.ui.inventory;

import java.util.Objects;

public class InventoryItem {
    private String itemName, itemNumber, location;
    private int quantity;

    // Constructor
    public InventoryItem(String itemName, String itemNumber, String location, int quantity) {
        this.itemName = itemName;
        this.itemNumber = itemNumber;
        this.location = location;
        this.quantity = quantity;
    }

    // Getter methods
    public String getItemName() {
        return itemName;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public String getLocation() {
        return location;
    }

    public int getQuantity() {
        return quantity;
    }

    // Optional: Setter methods (if you plan to update values)
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Optional: Override toString() for better logging/debugging
    @Override
    public String toString() {
        return "Item Name: " + itemName + ", Item Number: " + itemNumber + ", Location: " + location + ", Quantity: " + quantity;
    }

    // Optional: Override equals() and hashCode() for comparisons (useful for collections)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        InventoryItem that = (InventoryItem) obj;
        return quantity == that.quantity &&
                itemName.equals(that.itemName) &&
                itemNumber.equals(that.itemNumber) &&
                location.equals(that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName, itemNumber, location, quantity);
    }
}
