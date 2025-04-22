package com.example.tandonku;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateEmailActivity extends AppCompatActivity {

    private TextView txtCurrentEmail;
    private EditText edtOldEmail, edtNewEmail;
    private Button btnSubmit;

    // Contoh email awal (biasanya dari database/shared preferences)
    private String currentEmail = "example@email.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);

        // Inisialisasi komponen
        txtCurrentEmail = findViewById(R.id.txtCurrentEmail);
        edtOldEmail = findViewById(R.id.edtOldEmail);
        edtNewEmail = findViewById(R.id.edtNewEmail);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Tampilkan email saat ini
        txtCurrentEmail.setText(currentEmail);

        // Logika saat tombol "Kirim" ditekan
        btnSubmit.setOnClickListener(v -> {
            String oldEmail = edtOldEmail.getText().toString().trim();
            String newEmail = edtNewEmail.getText().toString().trim();

            if (!oldEmail.equals(currentEmail)) {
                Toast.makeText(this, "Email lama tidak cocok", Toast.LENGTH_SHORT).show();
            } else if (newEmail.isEmpty()) {
                Toast.makeText(this, "Email baru tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else {
                // Simpan email baru (contohnya bisa gunakan SharedPreferences)
                currentEmail = newEmail;
                txtCurrentEmail.setText(currentEmail);
                Toast.makeText(this, "Email berhasil diperbarui", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
