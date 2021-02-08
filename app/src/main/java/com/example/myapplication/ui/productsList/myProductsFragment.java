package com.example.myapplication.ui.productsList;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.model.Product;
import com.example.myapplication.service.ShoppingListManager;
import com.example.myapplication.ui.RecyclerViewAdapters.ProductAdapter;
import com.example.myapplication.ui.RecyclerViewAdapters.ProductInShoppingListAdapter;

import java.util.ArrayList;


public class myProductsFragment extends Fragment implements ProductInShoppingListAdapter.ListItemClickListener, AdapterView.OnItemSelectedListener {
    private ArrayList<Product> products;
    private RecyclerView recyclerView;
    private ShoppingListManager manager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.products_in_shopping_list_layout, container, false);
        recyclerView = root.findViewById(R.id.products_in_shopping_list);
        manager = new ShoppingListManager(getContext());
        refreshViews();

        Button btn_Done = (Button) root.findViewById(R.id.button_shopping_done);
        btn_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.deleteShoppingList(getArguments().getInt("newListID"));
                Navigation.findNavController(view).navigate(R.id.nav_home);
            }
        });
        return root;
    }


    private void refreshViews() {
        products = manager.getAllProductsForShoppingList(getArguments().getInt("newListID"));
        if (products.size()!=0) {
            ProductInShoppingListAdapter prodAdapter = new ProductInShoppingListAdapter(getContext(), products, this);
            recyclerView.setAdapter(prodAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }
    @Override
    public void onListItemClick(int position) {
        Product selected = products.get(position);
        manager.deleteProduct(selected);
        refreshViews();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}