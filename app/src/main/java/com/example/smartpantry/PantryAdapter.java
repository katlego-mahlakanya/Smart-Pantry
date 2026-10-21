package com.example.smartpantry;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.ItemHolder> {
    public interface Listener { void onEdit(PantryItem item); void onDelete(PantryItem item); }
    private final List<PantryItem> items;
    private final Listener listener;
    public PantryAdapter(List<PantryItem> items, Listener listener) { this.items = items; this.listener = listener; }
    @NonNull @Override public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pantry_item, parent, false); return new ItemHolder(view);
    }
    @Override public void onBindViewHolder(@NonNull ItemHolder h, int position) {
        PantryItem item = items.get(position); h.name.setText(item.name);
        String details = item.quantity + " " + item.unit;
        if (item.expiry != null && !item.expiry.trim().isEmpty()) details += " • Expires: " + item.expiry;
        h.details.setText(details);
        h.edit.setOnClickListener(v -> listener.onEdit(item)); h.delete.setOnClickListener(v -> listener.onDelete(item));
    }
    @Override public int getItemCount() { return items.size(); }
    static class ItemHolder extends RecyclerView.ViewHolder {
        TextView name, details; Button edit, delete;
        ItemHolder(View v) { super(v); name = v.findViewById(R.id.txtItemName); details = v.findViewById(R.id.txtItemDetails); edit = v.findViewById(R.id.btnEditItem); delete = v.findViewById(R.id.btnDeleteItem); }
    }
}
