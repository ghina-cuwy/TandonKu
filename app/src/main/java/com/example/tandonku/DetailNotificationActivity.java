package com.example.tandonku;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailNotificationActivity extends AppCompatActivity {

    TextView textTanggalWaktu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_notification);

        // Hubungkan ke TextView di XML
        textTanggalWaktu = findViewById(R.id.textTanggalWaktu);

        // Ambil waktu sekarang
        String currentDateTime = getCurrentDateTime();
        textTanggalWaktu.setText(currentDateTime);
    }

    private String getCurrentDateTime() {
        // Format ke: 1 April 2025, 14:35 WIB
        SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy, HH:mm 'WIB'", new Locale("id", "ID"));
        return sdf.format(new Date());
    }
}
