package com.example.smartpantry;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    public long id;
    public String name, steps;
    public List<RecipeIngredient> ingredients = new ArrayList<>();
    public Recipe(long id, String name, String steps) { this.id = id; this.name = name; this.steps = steps; }

    public static class RecipeIngredient {
        public String name, unit;
        public double quantity;
        public RecipeIngredient(String name, double quantity, String unit) { this.name = name; this.quantity = quantity; this.unit = unit; }
    }
}
