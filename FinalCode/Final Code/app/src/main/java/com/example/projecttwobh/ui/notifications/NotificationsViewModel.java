package com.example.projecttwobh.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class NotificationsViewModel extends ViewModel {

    // LiveData to hold a list of notifications
    private final MutableLiveData<List<String>> notifications;

    public NotificationsViewModel() {
        // Initialize with an empty list of notifications
        notifications = new MutableLiveData<>();
        notifications.setValue(new ArrayList<>());
    }

    // Getter for the notifications LiveData
    public LiveData<List<String>> getNotifications() {
        return notifications;
    }

    // Method to update the entire list of notifications
    public void setNotifications(List<String> newNotifications) {
        notifications.setValue(newNotifications);
    }

    // Method to add a single notification to the list
    public void addNotification(String notification) {
        List<String> currentNotifications = notifications.getValue();
        if (currentNotifications == null) {
            currentNotifications = new ArrayList<>();
        }
        currentNotifications.add(notification);
        notifications.setValue(currentNotifications);
    }

    // Method to clear all notifications
    public void clearNotifications() {
        notifications.setValue(new ArrayList<>());
    }
}
