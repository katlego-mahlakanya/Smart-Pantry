package com.example.smartpantry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "smart_pantry.db";
    private static final int DB_VERSION = 1;
    public DatabaseHelper(Context context) { super(context, DB_NAME, null, DB_VERSION); }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE pantry (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, quantity REAL NOT NULL, unit TEXT NOT NULL, expiry TEXT)");
        db.execSQL("CREATE TABLE recipes (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE NOT NULL, steps TEXT NOT NULL)");
        db.execSQL("CREATE TABLE recipe_ingredients (id INTEGER PRIMARY KEY AUTOINCREMENT, recipe_id INTEGER NOT NULL, name TEXT NOT NULL, quantity REAL NOT NULL, unit TEXT NOT NULL, FOREIGN KEY(recipe_id) REFERENCES recipes(id))");
        seedRecipes(db);
    }
    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS recipe_ingredients"); db.execSQL("DROP TABLE IF EXISTS recipes"); db.execSQL("DROP TABLE IF EXISTS pantry"); onCreate(db);
    }

    private void seedRecipes(SQLiteDatabase db) {
        addRecipe(db, "Tomato Toast", "Toast the bread. Slice the tomato and place it on top. Season if desired.", new String[][]{{"bread","2","pieces"},{"tomato","1","piece"}});
        addRecipe(db, "Cheese Toast", "Place cheese on bread and toast in a pan or toaster oven until melted.", new String[][]{{"bread","2","pieces"},{"cheese","1","slice"}});
        addRecipe(db, "Tomato Cheese Sandwich", "Place sliced tomato and cheese between two bread slices and serve.", new String[][]{{"bread","2","pieces"},{"tomato","1","piece"},{"cheese","1","slice"}});
        addRecipe(db, "Banana Oat Bowl", "Mash the banana and mix with oats and milk.", new String[][]{{"banana","1","piece"},{"oats","50","g"},{"milk","100","ml"}});
        addRecipe(db, "Simple Pancakes", "Whisk flour, egg and milk. Cook small portions in a lightly greased pan until done.", new String[][]{{"flour","100","g"},{"egg","1","piece"},{"milk","100","ml"}});
        addRecipe(db, "Egg Toast", "Cook the egg in a pan and serve with toasted bread.", new String[][]{{"egg","1","piece"},{"bread","2","pieces"}});
        addRecipe(db, "Garlic Tomato Pasta", "Cook pasta. Warm chopped tomato with garlic, combine and serve.", new String[][]{{"pasta","100","g"},{"tomato","1","piece"},{"garlic","1","clove"}});
        addRecipe(db, "Cheesy Pasta", "Cook pasta, drain, then stir in cheese until melted.", new String[][]{{"pasta","100","g"},{"cheese","1","slice"}});
        addRecipe(db, "Apple Oat Bowl", "Chop the apple and mix with oats and yoghurt.", new String[][]{{"apple","1","piece"},{"oats","50","g"},{"yoghurt","100","g"}});
        addRecipe(db, "Peanut Banana Toast", "Spread peanut butter on bread and add banana slices.", new String[][]{{"bread","2","pieces"},{"peanut butter","1","tablespoon"},{"banana","1","piece"}});
        addRecipe(db, "Potato Mash", "Boil potato until soft, drain, then mash with milk.", new String[][]{{"potato","1","piece"},{"milk","50","ml"}});
        addRecipe(db, "Carrot Salad", "Grate the carrot and mix with lemon juice.", new String[][]{{"carrot","1","piece"},{"lemon","1","piece"}});
        addRecipe(db, "Simple Omelette", "Beat eggs, cook in a pan and fold when set.", new String[][]{{"egg","2","pieces"}});
        addRecipe(db, "Yoghurt Fruit Bowl", "Chop the banana and apple, then add yoghurt.", new String[][]{{"banana","1","piece"},{"apple","1","piece"},{"yoghurt","100","g"}});
        addRecipe(db, "Tomato Egg Skillet", "Cook chopped tomato in a pan, add beaten egg and cook through.", new String[][]{{"tomato","1","piece"},{"egg","2","pieces"}});
        addRecipe(db, "Garlic Cheese Toast", "Mix finely chopped garlic with cheese, spread on bread and toast.", new String[][]{{"garlic","1","clove"},{"cheese","1","slice"},{"bread","2","pieces"}});
        addRecipe(db, "Lemon Pasta", "Cook pasta and toss with lemon juice and a little cooking water.", new String[][]{{"pasta","100","g"},{"lemon","1","piece"}});
        addRecipe(db, "Carrot Potato Soup", "Boil chopped carrot and potato in water until soft, then mash or blend.", new String[][]{{"carrot","1","piece"},{"potato","1","piece"}});
        addRecipe(db, "Milk Oat Porridge", "Simmer oats in milk, stirring until soft.", new String[][]{{"oats","50","g"},{"milk","150","ml"}});
        addRecipe(db, "Apple Banana Yoghurt", "Chop the fruit and mix with yoghurt.", new String[][]{{"apple","1","piece"},{"banana","1","piece"},{"yoghurt","100","g"}});
    }

    private void addRecipe(SQLiteDatabase db, String name, String steps, String[][] ingredients) {
        ContentValues recipe = new ContentValues(); recipe.put("name", name); recipe.put("steps", steps);
        long id = db.insert("recipes", null, recipe);
        for (String[] ing : ingredients) { ContentValues v = new ContentValues(); v.put("recipe_id", id); v.put("name", ing[0]); v.put("quantity", Double.parseDouble(ing[1])); v.put("unit", ing[2]); db.insert("recipe_ingredients", null, v); }
    }

    public long addPantryItem(String name, double quantity, String unit, String expiry) {
        ContentValues v = new ContentValues(); v.put("name", name.trim()); v.put("quantity", quantity); v.put("unit", unit.trim()); v.put("expiry", expiry == null ? "" : expiry.trim());
        return getWritableDatabase().insert("pantry", null, v);
    }
    public int updatePantryItem(long id, String name, double quantity, String unit, String expiry) {
        ContentValues v = new ContentValues(); v.put("name", name.trim()); v.put("quantity", quantity); v.put("unit", unit.trim()); v.put("expiry", expiry == null ? "" : expiry.trim());
        return getWritableDatabase().update("pantry", v, "id=?", new String[]{String.valueOf(id)});
    }
    public int deletePantryItem(long id) { return getWritableDatabase().delete("pantry", "id=?", new String[]{String.valueOf(id)}); }
    public PantryItem getPantryItem(long id) {
        Cursor c = getReadableDatabase().query("pantry", null, "id=?", new String[]{String.valueOf(id)}, null, null, null);
        try { if (c.moveToFirst()) return pantryFromCursor(c); return null; } finally { c.close(); }
    }
    private PantryItem pantryFromCursor(Cursor c) { return new PantryItem(c.getLong(c.getColumnIndexOrThrow("id")), c.getString(c.getColumnIndexOrThrow("name")), c.getDouble(c.getColumnIndexOrThrow("quantity")), c.getString(c.getColumnIndexOrThrow("unit")), c.getString(c.getColumnIndexOrThrow("expiry"))); }
    public List<PantryItem> getAllPantryItems() {
        List<PantryItem> list = new ArrayList<>(); Cursor c = getReadableDatabase().query("pantry", null, null, null, null, null, "name COLLATE NOCASE ASC");
        try { while (c.moveToNext()) list.add(pantryFromCursor(c)); } finally { c.close(); } return list;
    }
    public List<Recipe> getAllRecipes() {
        List<Recipe> list = new ArrayList<>(); SQLiteDatabase db = getReadableDatabase(); Cursor c = db.query("recipes", null, null, null, null, null, "name ASC");
        try { while (c.moveToNext()) { Recipe r = new Recipe(c.getLong(c.getColumnIndexOrThrow("id")), c.getString(c.getColumnIndexOrThrow("name")), c.getString(c.getColumnIndexOrThrow("steps"))); Cursor ic = db.query("recipe_ingredients", null, "recipe_id=?", new String[]{String.valueOf(r.id)}, null, null, "name ASC"); try { while (ic.moveToNext()) r.ingredients.add(new Recipe.RecipeIngredient(ic.getString(ic.getColumnIndexOrThrow("name")), ic.getDouble(ic.getColumnIndexOrThrow("quantity")), ic.getString(ic.getColumnIndexOrThrow("unit")))); } finally { ic.close(); } list.add(r); } } finally { c.close(); } return list;
    }
    public Recipe getRecipe(long id) { for (Recipe r : getAllRecipes()) if (r.id == id) return r; return null; }
}
