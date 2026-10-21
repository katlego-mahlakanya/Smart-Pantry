package com.example.smartpantry;

public class PantryItem {
    public long id;
    public String name, unit, expiry_date;
    public double quantity;

    public PantryItem(long id, String name, double quantity, String unit, String expiry_date) {
        this.id = id; this.name = name; this.quantity = quantity; this.unit = unit; this.expiry_date = expiry_date == null ? "" : expiry_date;
    }
}
