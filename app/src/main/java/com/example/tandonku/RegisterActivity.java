package com.example.tandonku;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText editName, editEmail, editPassword, editPhone;
    private Button buttonRegister;
    private ImageButton backButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editPhone = findViewById(R.id.editPhone);
        buttonRegister = findViewById(R.id.buttonRegister);
        backButton = findViewById(R.id.backButton);

        buttonRegister.setOnClickListener(v -> {
            String nama = editName.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();
            String phone = editPhone.getText().toString().trim();

            if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(email)
                    || TextUtils.isEmpty(password) || TextUtils.isEmpty(phone)) {
                Toast.makeText(this, "Harap isi semua data!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Register ke Firebase Authentication
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Pendaftaran berhasil!", Toast.LENGTH_SHORT).show();

                            // Setelah register, kirim email ke LoginActivity
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            intent.putExtra("email_terdaftar", email);
                            startActivity(intent);
                            finish();
                        } else {
                            String errorMsg = task.getException() != null ? task.getException().getMessage() : "Pendaftaran gagal.";
                            Toast.makeText(this, "Gagal daftar: " + errorMsg, Toast.LENGTH_LONG).show();
                        }
                    });
        });

        backButton.setOnClickListener(v -> finish());
    }
}
