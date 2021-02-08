package com.example.myapplication.ui.productsList;

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

import java.util.ArrayList;


public class myProductsInShoppingListFragment extends Fragment implements ProductAdapter.ListItemClickListener, AdapterView.OnItemSelectedListener {
    private ArrayList<Product> products;
    private RecyclerView recyclerView;
    private ShoppingListManager manager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.new_products_in_shopping_list_creation, container, false);
        recyclerView = root.findViewById(R.id.product_list);
        manager = new ShoppingListManager(getContext());
        refreshViews();
        setAdapter(root);
        final TextView newProductName = root.findViewById(R.id.new_product_name);
        final TextView newProductQuantity = root.findViewById(R.id.new_product_quantity);
        final Spinner newProductUnitSpinner = root.findViewById(R.id.unit_spinner);

        Button btn_addProduct = (Button) root.findViewById(R.id.create_new_product);
        btn_addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productName = newProductName.getText().toString();
                String productQuantity = newProductQuantity.getText().toString();
                String productUnit = newProductUnitSpinner.getSelectedItem().toString();
                Product newProd = new Product(productName, Double.valueOf(productQuantity), productUnit,getArguments().getInt("newListID"));
                int newProdId = manager.addProduct(newProd);
                newProd.setId(newProdId);
                products.add(newProd);
                refreshViews();
            }
        });
        Button btn_Done = (Button) root.findViewById(R.id.new_product_done);
        btn_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_home);
            }
        });
        return root;
    }

    private void setAdapter(View root) {
        String[] units = { "Kilograms", "Grams", "Liters", "Mililiters", "Items" };
        Spinner spin = (Spinner) root.findViewById(R.id.unit_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
    }
    private void refreshViews() {
        products = manager.getAllProductsForShoppingList(getArguments().getInt("newListID"));
        if (products.size()!=0) {
            ProductAdapter prodAdapter = new ProductAdapter(getContext(), products, this);
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