package com.example.smartpantry;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private CheckBox expiry;
    private SharedPreferences preferences;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_settings);
        preferences = getSharedPreferences("pantry_settings", MODE_PRIVATE); expiry = findViewById(R.id.checkExpiry);
        expiry.setChecked(preferences.getBoolean("expiry_alerts", true));
        findViewById(R.id.btnSaveSettings).setOnClickListener(v -> { preferences.edit().putBoolean("expiry_alerts", expiry.isChecked()).apply(); Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show(); });
        findViewById(R.id.btnSettingsBack).setOnClickListener(v -> finish());
    }
}
