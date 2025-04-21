package com.example.tandonku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText editEmail, editPassword;
    private Button buttonLogin;
    private TextView buttonRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inisialisasi FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);

        // Ambil email dari intent jika ada (misalnya setelah registrasi)
        Intent incomingIntent = getIntent();
        if (incomingIntent.hasExtra("email_terdaftar")) {
            String emailTerdaftar = incomingIntent.getStringExtra("email_terdaftar");
            editEmail.setText(emailTerdaftar);
        }

        // Tombol login
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Isi semua data!", Toast.LENGTH_SHORT).show();
                } else {
                    // Proses login Firebase
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, task -> {
                                if (task.isSuccessful()) {
                                    // Login berhasil
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(LoginActivity.this, "Login berhasil!", Toast.LENGTH_SHORT).show();

                                    // Pindah ke halaman profil
                                    Intent profileIntent = new Intent(LoginActivity.this, ProfileActivity.class);
                                    startActivity(profileIntent);
                                    finish();
                                } else {
                                    // Login gagal
                                    Toast.makeText(LoginActivity.this, "Login gagal. Periksa email atau password!", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        // Tombol daftar
        buttonRegister.setOnClickListener(v -> {
            Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        });
    }
}
