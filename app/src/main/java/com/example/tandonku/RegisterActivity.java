package com.example.tandonku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText editName, editEmail, editPassword, editPhone;
    private Button buttonRegister;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editPhone = findViewById(R.id.editPhone);
        buttonRegister = findViewById(R.id.buttonRegister);
        backButton = findViewById(R.id.backButton);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = "alya";
                String email = "alya25@gmail.com";
                String password = "2005";
                String phone = "12345678";


                if (nama.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Harap isi semua data", Toast.LENGTH_SHORT).show();
                } else {
                    // Simulasi proses pendaftaran
                    Toast.makeText(RegisterActivity.this, "Pendaftaran berhasil!", Toast.LENGTH_SHORT).show();

                    // Pindah ke halaman Login dan kirim email
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.putExtra("email_terdaftar", email); // KIRIM email ke LoginActivity
                    startActivity(intent);
                    finish(); // supaya user gak balik ke sini
                }
            }
        });



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kembali ke activity sebelumnya
            }
        });
    }
}