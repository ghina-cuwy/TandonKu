package com.example.tandonku;

import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationActivity extends AppCompatActivity {

    LinearLayout notificationItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notificationItem = findViewById(R.id.notificationItem);

        notificationItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationActivity.this, DetailNotificationActivity.class);
                startActivity(intent);
            }
        });
    }
}

