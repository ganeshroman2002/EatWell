package com.example.eatwell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private EditText etAge, etHeight, etWeight, etGoal;
    private AutoCompleteTextView dropdownGender, dropdownDietType;
    private Button btnSaveProfile;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize Firebase Auth and Database Reference
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Initialize views
        etAge = findViewById(R.id.et_age);
        etHeight = findViewById(R.id.et_height);
        etWeight = findViewById(R.id.et_weight);
        etGoal = findViewById(R.id.et_goal);
        dropdownGender = findViewById(R.id.dropdown_gender);
        dropdownDietType = findViewById(R.id.dropdown_diet_type);
        btnSaveProfile = findViewById(R.id.btn_save_profile);
        progressBar = findViewById(R.id.progress_bar);

        // Setup gender dropdown
        String[] genderOptions = new String[]{"Male", "Female", "Other", "Prefer not to say"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, genderOptions);
        dropdownGender.setAdapter(genderAdapter);

        // Setup diet type dropdown
        String[] dietTypeOptions = new String[]{"Weight Loss Diet", "Weight Gain Diet",
                "Weight Maintenance Diet", "Other"};
        ArrayAdapter<String> dietAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, dietTypeOptions);
        dropdownDietType.setAdapter(dietAdapter);

        // Save profile button click listener
        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserProfile();
            }
        });
    }

    private void saveUserProfile() {
        String age = etAge.getText().toString().trim();
        String gender = dropdownGender.getText().toString().trim();
        String height = etHeight.getText().toString().trim();
        String weight = etWeight.getText().toString().trim();
        String dietType = dropdownDietType.getText().toString().trim();
        String goal = etGoal.getText().toString().trim();

        // Validate input
        if (age.isEmpty() || gender.isEmpty() || height.isEmpty() ||
                weight.isEmpty() || dietType.isEmpty() || goal.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show progress bar
        progressBar.setVisibility(View.VISIBLE);

        // Get current user
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            // User not logged in
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
            redirectToLogin();
            return;
        }

        // Create profile data map
        Map<String, Object> profileData = new HashMap<>();
        profileData.put("age", age);
        profileData.put("gender", gender);
        profileData.put("height", height);
        profileData.put("weight", weight);
        profileData.put("dietType", dietType);
        profileData.put("goal", goal);
        profileData.put("profileCompleted", true);

        // Save to Realtime Database under 'users/{userId}/profile'
        databaseReference.child(user.getUid()).child("profile").setValue(profileData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "Profile saved successfully", Toast.LENGTH_SHORT).show();
                            // Redirect to main/home activity
                            redirectToMainActivity();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Failed to save profile: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void redirectToLogin() {
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void redirectToMainActivity() {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}