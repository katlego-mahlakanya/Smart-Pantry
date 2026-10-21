package com.example.smartpantry;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PantryFormActivity extends AppCompatActivity {
    private DatabaseHelper db; private EditText name, quantity, unit, expiry; private long itemId = -1;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_pantry_form); db = new DatabaseHelper(this);
        name = findViewById(R.id.editName); quantity = findViewById(R.id.editQuantity); unit = findViewById(R.id.editUnit); expiry = findViewById(R.id.editExpiry);
        itemId = getIntent().getLongExtra("item_id", -1);
        if (itemId != -1) { ((TextView)findViewById(R.id.txtFormTitle)).setText("Edit ingredient"); PantryItem item = db.getPantryItem(itemId); if (item != null) { name.setText(item.name); quantity.setText(String.valueOf(item.quantity)); unit.setText(item.unit); expiry.setText(item.expiry); } }
        findViewById(R.id.btnSave).setOnClickListener(v -> saveItem());
    }
    private void saveItem() {
        String n = name.getText().toString().trim(), q = quantity.getText().toString().trim(), u = unit.getText().toString().trim(), e = expiry.getText().toString().trim();
        if (n.isEmpty() || q.isEmpty() || u.isEmpty()) { Toast.makeText(this, "Enter ingredient name, quantity and unit.", Toast.LENGTH_LONG).show(); return; }
        double amount; try { amount = Double.parseDouble(q); } catch (NumberFormatException ex) { Toast.makeText(this, "Quantity must be a number.", Toast.LENGTH_LONG).show(); return; }
        if (amount <= 0) { Toast.makeText(this, "Quantity must be greater than zero.", Toast.LENGTH_LONG).show(); return; }
        if (!e.isEmpty() && !e.matches("\\d{4}-\\d{2}-\\d{2}")) { Toast.makeText(this, "Use expiry format YYYY-MM-DD, or leave it blank.", Toast.LENGTH_LONG).show(); return; }
        long result;
        if (itemId == -1) result = db.addPantryItem(n, amount, u, e); else result = db.updatePantryItem(itemId, n, amount, u, e);
        if (result == -1) { Toast.makeText(this, "Could not save ingredient.", Toast.LENGTH_LONG).show(); return; }
        Toast.makeText(this, itemId == -1 ? "Ingredient added" : "Ingredient updated", Toast.LENGTH_SHORT).show(); finish();
    }
}
