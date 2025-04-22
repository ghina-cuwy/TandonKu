package com.example.tandonku;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePasswordActivity extends AppCompatActivity {

    TextView txtCurrentPassword; // optional, bisa kamu gunakan untuk menampilkan placeholder
    EditText edtOldPassword, edtNewPassword;
    Button btnSubmitPassword;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        // Inisialisasi Firebase
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        // Inisialisasi View
        txtCurrentPassword = findViewById(R.id.txtCurrentPassword); // bisa kamu tampilkan teks dummy
        edtOldPassword = findViewById(R.id.edtOldPassword);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        btnSubmitPassword = findViewById(R.id.btnSubmitPassword);

        btnSubmitPassword.setOnClickListener(v -> {
            String oldPassword = edtOldPassword.getText().toString().trim();
            String newPassword = edtNewPassword.getText().toString().trim();

            if (TextUtils.isEmpty(oldPassword) || TextUtils.isEmpty(newPassword)) {
                Toast.makeText(this, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            if (newPassword.length() < 6) {
                Toast.makeText(this, "Password baru minimal 6 karakter", Toast.LENGTH_SHORT).show();
                return;
            }

            // Ambil email login untuk autentikasi ulang
            String userEmail = currentUser.getEmail();

            // Re-authenticate dengan password lama
            AuthCredential credential = EmailAuthProvider.getCredential(userEmail, oldPassword);
            currentUser.reauthenticate(credential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Update password
                    currentUser.updatePassword(newPassword).addOnCompleteListener(updateTask -> {
                        if (updateTask.isSuccessful()) {
                            Toast.makeText(this, "Password berhasil diperbarui", Toast.LENGTH_SHORT).show();
                            // Arahkan ke halaman profil atau logout ulang
                            Intent i = new Intent(this, ProfileActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(this, "Gagal memperbarui password", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(this, "Password lama salah", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
