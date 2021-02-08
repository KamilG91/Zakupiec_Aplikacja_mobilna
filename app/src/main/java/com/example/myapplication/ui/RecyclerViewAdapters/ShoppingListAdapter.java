package com.example.myapplication.ui.RecyclerViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.ShoppingList;

import java.util.ArrayList;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder> {
    private Context context;
    private ArrayList<ShoppingList> shoppingLists;
    final private ShoppingListAdapter.ShoppingListClickListener onClickListener;

    public interface ShoppingListClickListener{
        void onShoppingListListItemClick(int position);
    }

    public ShoppingListAdapter(Context context, ArrayList<ShoppingList> shoppingLists, ShoppingListAdapter.ShoppingListClickListener onClickListener) {
        this.context = context;
        this.shoppingLists = shoppingLists;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ShoppingListAdapter.ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_item_for_product_in_shopping_list, parent, false);
        return new ShoppingListAdapter.ShoppingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListAdapter.ShoppingListViewHolder holder, int position) {
        ShoppingList shoppingList = shoppingLists.get(position);
        String shoppingListNameLabel = context.getString(R.string.shopping_list_row_name) + shoppingList.getName() != null ? shoppingList.getName() : "";
        holder.shoppingListName.setText(shoppingListNameLabel);
        String shoppingListDateLabel = context.getString(R.string.shopping_list_row_date) + shoppingList.getDateTime() != null ? shoppingList.getDateTime().toString() : "";
        holder.shoppingListDate.setText(shoppingListDateLabel);
    }

    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }

    public class ShoppingListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView shoppingListName, shoppingListDate;

        public ShoppingListViewHolder(@NonNull View itemView) {
            super(itemView);
            shoppingListName = itemView.findViewById(R.id.shopping_list_name);
            shoppingListDate = itemView.findViewById(R.id.shopping_list_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            onClickListener.onShoppingListListItemClick(position);
        }
    }
}