package com.example.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {
    private DatabaseHelper db;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_suggested_recipes); db = new DatabaseHelper(this);
        findViewById(R.id.btnBackPantry).setOnClickListener(v -> finish()); showRecipes();
    }
    private void showRecipes() {
        LinearLayout container = findViewById(R.id.recipeContainer); container.removeAllViews();
        List<Recipe> matches = RecipeMatcher.findStrictMatches(db.getAllRecipes(), db.getAllPantryItems());
        if (matches.isEmpty()) { TextView empty = new TextView(this); empty.setText("No recipes match your pantry yet. Add ingredients or check the quantities and units."); empty.setTextSize(17); empty.setPadding(8, 20, 8, 20); container.addView(empty); return; }
        for (Recipe recipe : matches) {
            Button b = new Button(this); b.setAllCaps(false); b.setText(recipe.name + "\n" + recipe.ingredients.size() + " required ingredients"); b.setGravity(android.view.Gravity.CENTER_VERTICAL); b.setPadding(16, 8, 16, 8);
            b.setOnClickListener(v -> { Intent i = new Intent(this, RecipeDetailActivity.class); i.putExtra("recipe_id", recipe.id); startActivity(i); });
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); lp.bottomMargin = 8; container.addView(b, lp);
        }
    }
}
