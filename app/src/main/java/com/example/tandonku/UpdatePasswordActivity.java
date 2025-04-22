package com.example.tandonku;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdatePasswordActivity extends AppCompatActivity {

    private TextView txtCurrentPassword;
    private EditText edtOldPassword, edtNewPassword;
    private Button btnSubmitPassword;

    // Simulasi password tersimpan
    private String currentPassword = "12345678";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        txtCurrentPassword = findViewById(R.id.txtCurrentPassword);
        edtOldPassword = findViewById(R.id.edtOldPassword);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        btnSubmitPassword = findViewById(R.id.btnSubmitPassword);

        // Menampilkan password dalam bentuk bintang
        txtCurrentPassword.setText(currentPassword.replaceAll(".", "*"));

        btnSubmitPassword.setOnClickListener(v -> {
            String oldPass = edtOldPassword.getText().toString().trim();
            String newPass = edtNewPassword.getText().toString().trim();

            if (!oldPass.equals(currentPassword)) {
                Toast.makeText(this, "Password lama tidak cocok", Toast.LENGTH_SHORT).show();
            } else if (newPass.isEmpty()) {
                Toast.makeText(this, "Password baru tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else {
                currentPassword = newPass;
                txtCurrentPassword.setText(currentPassword.replaceAll(".", "*"));
                Toast.makeText(this, "Password berhasil diperbarui", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
