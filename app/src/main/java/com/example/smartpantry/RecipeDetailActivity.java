package com.example.smartpantry;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); setContentView(R.layout.activity_recipe_detail);
        long id = getIntent().getLongExtra("recipe_id", -1); Recipe recipe = new DatabaseHelper(this).getRecipe(id);
        if (recipe == null) { Toast.makeText(this, "Recipe could not be found.", Toast.LENGTH_LONG).show(); finish(); return; }
        ((TextView)findViewById(R.id.txtRecipeName)).setText(recipe.name);
        StringBuilder ingredients = new StringBuilder();
        for (Recipe.RecipeIngredient ing : recipe.ingredients) ingredients.append("• ").append(ing.quantity).append(" ").append(ing.unit).append(" ").append(ing.name).append("\n");
        ((TextView)findViewById(R.id.txtRecipeIngredients)).setText(ingredients.toString());
        ((TextView)findViewById(R.id.txtRecipeSteps)).setText(recipe.steps);
        findViewById(R.id.btnRecipeBack).setOnClickListener(v -> finish());
    }
}
