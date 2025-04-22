package com.example.tandonku;

import android.Manifest;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

        // Inisialisasi komponen
        imageProfile = findViewById(R.id.imageProfile);
        emailItem = findViewById(R.id.emailItem);
        passwordItem = findViewById(R.id.passwordItem);
        powerItem = findViewById(R.id.powerItem);
        logoutItem = findViewById(R.id.logoutItem);
        textEmail = findViewById(R.id.textEmail);

        // Ambil email dari akun yang sedang login
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String currentEmail = user.getEmail();
            textEmail.setText(currentEmail);

            // Klik Email: pindah ke UpdateEmailActivity dan kirim email saat ini
            emailItem.setOnClickListener(v -> {
                Intent intent = new Intent(ProfileActivity.this, UpdateEmailActivity.class);
                intent.putExtra("currentEmail", currentEmail);
                startActivity(intent);
            });
        } else {
            textEmail.setText("Tidak ada email");
        }

        // Klik gambar profil
        imageProfile.setOnClickListener(view -> showImageOptions());

        // Klik Password: pindah ke halaman UpdatePasswordActivity
        passwordItem.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, UpdatePasswordActivity.class);
            startActivity(intent);
        });

        // Klik ON/OFF
        powerItem.setOnClickListener(v -> {
            Toast.makeText(this, "Tombol ON/OFF ditekan", Toast.LENGTH_SHORT).show();
        });

        // Klik Logout
        logoutItem.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut(); // logout Firebase juga
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
                        if (ContextCompat.checkSelfPermission(this,
                                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_GALLERY);
                        } else {
                            pickImageFromGallery();
                        }
                    } else if (which == 1) {
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
