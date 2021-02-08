package com.example.myapplication.ui.RecyclerViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Product;

import java.util.ArrayList;

public class ProductInShoppingListAdapter extends RecyclerView.Adapter<ProductInShoppingListAdapter.ProductViewHolder> {
    private Context context;
    private ArrayList<Product> products;
    final private ListItemClickListener onClickListener;

    public interface ListItemClickListener{
        void onListItemClick(int position);
    }

    public ProductInShoppingListAdapter(Context context, ArrayList<Product> products, ListItemClickListener onClickListener) {
        this.context = context;
        this.products = products;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_item_for_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product prod = products.get(position);
        holder.name.setText(prod.getName());
        holder.quantity.setText(String.valueOf(prod.getQuantity()));
        holder.unit.setText(String.valueOf(prod.getUnit()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name, quantity, unit;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            quantity = itemView.findViewById(R.id.product_amount);
            unit = itemView.findViewById(R.id.product_unit);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            onClickListener.onListItemClick(position);
        }
    }
}
