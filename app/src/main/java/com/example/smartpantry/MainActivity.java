package com.example.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private RecyclerView recycler;
    private TextView count;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this); recycler = findViewById(R.id.recyclerPantry); count = findViewById(R.id.txtPantryCount);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.btnAdd).setOnClickListener(v -> startActivity(new Intent(this, PantryFormActivity.class)));
        findViewById(R.id.btnSuggestions).setOnClickListener(v -> startActivity(new Intent(this, SuggestedRecipesActivity.class)));
        findViewById(R.id.btnSettings).setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
    }
    @Override protected void onResume() { super.onResume(); if (db != null) loadPantry(); }
    private void loadPantry() {
        List<PantryItem> items = db.getAllPantryItems(); count.setText("Your ingredients (" + items.size() + ")");
        recycler.setAdapter(new PantryAdapter(items, new PantryAdapter.Listener() {
            @Override public void onEdit(PantryItem item) { Intent i = new Intent(MainActivity.this, PantryFormActivity.class); i.putExtra("item_id", item.id); startActivity(i); }
            @Override public void onDelete(PantryItem item) { new AlertDialog.Builder(MainActivity.this).setTitle("Delete ingredient?").setMessage("Remove " + item.name + " from your pantry?").setNegativeButton("Cancel", null).setPositiveButton("Delete", (d, w) -> { db.deletePantryItem(item.id); loadPantry(); Toast.makeText(MainActivity.this, "Ingredient deleted", Toast.LENGTH_SHORT).show(); }).show(); }
        }));
        if (items.isEmpty()) count.setText("Your pantry is empty. Tap Add ingredient to begin.");
    }
}
