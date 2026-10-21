package com.example.smartpantry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeMatcher {
    private RecipeMatcher() {}

    public static List<Recipe> findStrictMatches(List<Recipe> recipes, List<PantryItem> pantry) {
        java.util.ArrayList<Recipe> matches = new java.util.ArrayList<>();
        Map<String, Double> amounts = new HashMap<>();
        Map<String, String> units = new HashMap<>();
        for (PantryItem item : pantry) {
            String key = normalizeName(item.name);
            String unit = normalizeUnit(item.unit);
            if (key.isEmpty()) continue;
            if (!units.containsKey(key)) { units.put(key, unit); amounts.put(key, item.quantity); }
            else if (units.get(key).equals(unit)) amounts.put(key, amounts.get(key) + item.quantity);
        }
        for (Recipe recipe : recipes) {
            boolean canMake = true;
            for (Recipe.RecipeIngredient required : recipe.ingredients) {
                String key = normalizeName(required.name);
                String requiredUnit = normalizeUnit(required.unit);
                if (!amounts.containsKey(key) || !units.get(key).equals(requiredUnit) || amounts.get(key) + 0.000001 < required.quantity) {
                    canMake = false; break;
                }
            }
            if (canMake) matches.add(recipe);
        }
        return matches;
    }

    // Handles common singular/plural variations without fuzzy matching unrelated foods.
    public static String normalizeName(String name) {
        String s = name == null ? "" : name.trim().toLowerCase(java.util.Locale.ROOT).replaceAll("\\s+", " ");
        if (s.endsWith("ies") && s.length() > 4) return s.substring(0, s.length() - 3) + "y";
        if (s.endsWith("oes") && s.length() > 4) return s.substring(0, s.length() - 2);
        if (s.endsWith("s") && !s.endsWith("ss") && s.length() > 3) return s.substring(0, s.length() - 1);
        return s;
    }
    public static String normalizeUnit(String unit) {
        String s = unit == null ? "" : unit.trim().toLowerCase(java.util.Locale.ROOT);
        if (s.equals("piece") || s.equals("pcs") || s.equals("pc")) return "pieces";
        if (s.equals("gram") || s.equals("grams")) return "g";
        if (s.equals("millilitre") || s.equals("millilitres") || s.equals("milliliter") || s.equals("milliliters")) return "ml";
        if (s.equals("tablespoons") || s.equals("tbsp")) return "tablespoon";
        if (s.equals("cloves")) return "clove";
        if (s.equals("slices")) return "slice";
        return s;
    }
}
