package com.example.projecttwobh.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.projecttwobh.databinding.FragmentHomeBinding;

import java.util.List;

public class NotificationsFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textView20; // Ensure this matches the ID in your layout

        // Observe the list of notifications
        notificationsViewModel.getNotifications().observe(getViewLifecycleOwner(), notifications -> {
            if (notifications == null || notifications.isEmpty()) {
                textView.setText("No Notifications at This Time.");
            } else {
                StringBuilder notificationText = new StringBuilder("Current Notifications:\n");
                for (String notification : notifications) {
                    notificationText.append("- ").append(notification).append("\n");
                }
                textView.setText(notificationText.toString());
            }
        });

        // Example: Add a test notification (optional)
        notificationsViewModel.addNotification("Stock for Item A is running low.");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
