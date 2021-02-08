package com.example.myapplication.ui.myShoppingLists;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Product;
import com.example.myapplication.model.ShoppingList;
import com.example.myapplication.service.ShoppingListManager;
import com.example.myapplication.ui.RecyclerViewAdapters.ShoppingListAdapter;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class myShoppingListsFragment extends Fragment implements ShoppingListAdapter.ShoppingListClickListener {
    private RecyclerView myShoppingLists;
    private ShoppingListManager shoppingListManager;
    private ArrayList<ShoppingList> shoppingLists;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.my_shopping_lists_section_layout, container, false);
        shoppingListManager = new ShoppingListManager(getContext());
        myShoppingLists = root.findViewById(R.id.my_shopping_lists_recycler);
        refreshViews();

        return root;
    }
    public void refreshViews() {
        shoppingLists = shoppingListManager.getMyShoppingLists();
        ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter(getContext(), shoppingLists, this);
        myShoppingLists.setAdapter(shoppingListAdapter);
        myShoppingLists.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    @Override
    public void onShoppingListListItemClick(int position) {
        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.popup_start_shopping_remove_list,null, false);

        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setCancelable(false)
                .setTitle(getContext().getString(R.string.start_shopping_remove_list))
                .setView(viewInflated)
                .setPositiveButton(R.string.start_shopping, null)
                .setNegativeButton(R.string.remove_list,null)
                .show();


        Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("newListID", position + 1);
                NavController navController = Navigation.findNavController(getActivity(),  R.id.nav_zakupy_start);
                navController.navigate(R.id.nav_zakupy_start, bundle);
                alertDialog.dismiss();
            }
        });

        Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingList selected = shoppingLists.get(position);
                shoppingListManager.deleteShoppingList(selected.getId());
                refreshViews();
                alertDialog.cancel();
            }
        });
    }

}
