package com.example.eatwell.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eatwell.LoginActivity;
import com.example.eatwell.MainActivity;
import com.example.eatwell.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIMEOUT = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Initialize views
        ImageView logoImageView = findViewById(R.id.iv_logo);
        TextView appNameTextView = findViewById(R.id.tv_app_name);
        TextView taglineTextView = findViewById(R.id.tv_tagline);

        // Create fade-in animation
        Animation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(1500);

        // Apply animation to views
        logoImageView.startAnimation(fadeIn);
        appNameTextView.startAnimation(fadeIn);
        taglineTextView.startAnimation(fadeIn);

        // Using a handler to delay the start of the main activity
        new Handler().postDelayed(() -> {
            // Start your main activity
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);

            // Close this activity
            finish();
        }, SPLASH_TIMEOUT);
    }
}