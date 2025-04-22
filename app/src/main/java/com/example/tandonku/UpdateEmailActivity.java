package com.example.tandonku;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdateEmailActivity extends AppCompatActivity {

    TextView txtCurrentEmail;
    EditText edtOldEmail, edtPassword, edtNewEmail;
    Button btnSubmit;

    String currentEmail;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);

        // Firebase
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        // Ambil email dari intent atau user login
        currentEmail = getIntent().getStringExtra("currentEmail");
        if (currentEmail == null && currentUser != null) {
            currentEmail = currentUser.getEmail();
        }

        // Inisialisasi view
        txtCurrentEmail = findViewById(R.id.txtCurrentEmail);
        edtOldEmail = findViewById(R.id.edtOldEmail);
        edtPassword = findViewById(R.id.edtPassword); // tambahkan editText password di layout!
        edtNewEmail = findViewById(R.id.edtNewEmail);
        btnSubmit = findViewById(R.id.btnSubmit);

        txtCurrentEmail.setText(currentEmail);

        btnSubmit.setOnClickListener(v -> {
            String oldEmail = edtOldEmail.getText().toString().trim();
            String newEmail = edtNewEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (!oldEmail.equals(currentEmail)) {
                Toast.makeText(this, "Email lama tidak cocok", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Masukkan password untuk verifikasi", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(newEmail)) {
                Toast.makeText(this, "Email baru tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            // Re-authentication
            AuthCredential credential = EmailAuthProvider.getCredential(oldEmail, password);
            currentUser.reauthenticate(credential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    currentUser.updateEmail(newEmail)
                            .addOnCompleteListener(updateTask -> {
                                if (updateTask.isSuccessful()) {
                                    Toast.makeText(this, "Email berhasil diperbarui", Toast.LENGTH_SHORT).show();
                                    txtCurrentEmail.setText(newEmail);
                                    // Redirect ke profil atau logout ulang
                                    Intent i = new Intent(this, ProfileActivity.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(this, "Gagal memperbarui email", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(this, "Autentikasi gagal. Cek password kamu", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
