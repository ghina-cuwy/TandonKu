package com.example.tandonku;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ProfileActivity extends AppCompatActivity {

    private static final int REQUEST_GALLERY = 100;
    private ImageView imageProfile;
    private Uri imageUri = null;

    LinearLayout emailItem, passwordItem, powerItem, logoutItem;
    TextView textEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);  // pastikan layout yang benar dimuat

        // Cek apakah ID sudah benar
        imageProfile = findViewById(R.id.imageProfile);
        emailItem = findViewById(R.id.emailItem);
        passwordItem = findViewById(R.id.passwordItem);
        powerItem = findViewById(R.id.powerItem);
        logoutItem = findViewById(R.id.logoutItem);
        textEmail = findViewById(R.id.textEmail);

        // Dummy email
        textEmail.setText("user@email.com");

        // Tindakan klik untuk gambar profil
        imageProfile.setOnClickListener(view -> showImageOptions());

        // Tindakan klik untuk item lainnya
        emailItem.setOnClickListener(v -> {
            // pindah ke ganti email
        });

        passwordItem.setOnClickListener(v -> {
            // pindah ke ganti password
        });

        powerItem.setOnClickListener(v -> {
            Toast.makeText(this, "Tombol ON/OFF ditekan", Toast.LENGTH_SHORT).show();
        });

        logoutItem.setOnClickListener(v -> {
            // logout action
            Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        });
    }

    private void showImageOptions() {
        String[] options = {"Pilih dari Galeri", "Hapus Foto"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ubah Foto Profil")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        // dari galeri
                        if (ContextCompat.checkSelfPermission(this,
                                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_GALLERY);
                        } else {
                            pickImageFromGallery();
                        }
                    } else if (which == 1) {
                        // hapus foto
                        imageProfile.setImageResource(R.drawable.ic_profile_placeholder);
                        imageUri = null;
                    }
                });
        builder.show();
    }

    private void pickImageFromGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imageProfile.setImageURI(imageUri);
        }
    }
}
