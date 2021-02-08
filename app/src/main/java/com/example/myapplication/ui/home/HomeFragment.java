package com.example.myapplication.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myapplication.R;
import com.example.myapplication.model.ShoppingList;
import com.example.myapplication.service.ShoppingListManager;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;

import static android.view.View.INVISIBLE;

public class HomeFragment extends Fragment {
    private ShoppingListManager manager;
    private ArrayList<ShoppingList> shoppingLists;
    private int currentList;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_section_layout, container, false);
        View floatingPlus = getActivity().findViewById(R.id.fab);
        floatingPlus.setVisibility(INVISIBLE);
        manager = new ShoppingListManager(getContext());
        Button btn_ZakupyStart = (Button) root.findViewById(R.id.button_zacznij_zakupy);
        btn_ZakupyStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.popup_shopping_start_layout,null, false);
                shoppingLists = manager.getMyShoppingLists();
                String[] lists = new String[shoppingLists.size()];
                int i = 0;
                for  (ShoppingList list : shoppingLists) {
                    lists[i] = list.getId() + " " + list.getName();
                    i++;
                }
                currentList = i;
                Spinner spin = (Spinner) viewInflated.findViewById(R.id.shopping_list_spinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_spinner_item, lists);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin.setAdapter(adapter);

                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setCancelable(false)
                        .setTitle(getContext().getString(R.string.choose_shopping_list))
                        .setView(viewInflated)
                        .setPositiveButton(android.R.string.ok,null)
                        .setNegativeButton(android.R.string.cancel,null)
                        .show();


                Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        String productUnit = spin.getSelectedItem().toString();
                        String[] currentList = productUnit.split(" ");
                        bundle.putInt("newListID", Integer.parseInt(currentList[0]));
                        Navigation.findNavController(view).navigate(R.id.nav_zakupy_start, bundle);
                        alertDialog.dismiss();
                    }
                });

                Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
            }
        });
        Button btn_StworzListe = (Button) root.findViewById(R.id.button_stworz_liste);
        btn_StworzListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.popup_new_shopping_list,null, false);
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setCancelable(false)
                        .setTitle(getContext().getString(R.string.new_shopping_list_title))
                        .setView(viewInflated)
                        .setPositiveButton(android.R.string.ok,null)
                        .setNegativeButton(android.R.string.cancel,null)
                        .show();

                final EditText newShoppingListName = viewInflated.findViewById(R.id.popup_add_shopping_list_name);
                final TextView newShoppingListDate = viewInflated.findViewById(R.id.popup_add_shopping_list_date);
                newShoppingListDate.setText(new Date().toString());

                Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String shoppingListName = newShoppingListName.getText().toString();
                        String shoppingListDate = newShoppingListDate.getText().toString();
                        String validationResult = validateNewList(shoppingListName);
                        if (validationResult.isEmpty()) {
                            int newListID = manager.addShoppingList(shoppingListName, shoppingListDate);
                            alertDialog.dismiss();
                            Bundle bundle = new Bundle();
                            bundle.putInt("newListID", newListID);
                            Navigation.findNavController(view).navigate(R.id.nav_stworz_liste, bundle);
                        } else {
                            Snackbar.make(viewInflated, validationResult, BaseTransientBottomBar.LENGTH_SHORT).setTextColor(Color.BLACK).show();
                        }

                    }

                    public String validateNewList(String name) {
                        StringBuilder sb = new StringBuilder();
                        if (name == null || name.trim().isEmpty()) {
                            sb.append(getContext().getString(R.string.new_product_validate_name));
                            sb.append("\n");
                        }
                        return sb.toString();
                    }
                });

                Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
            }
        });
        Button btn_ListyZakupow = (Button) root.findViewById(R.id.button_listy_zakupow);
        btn_ListyZakupow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_listy_zakupów);
            }
        });
        return root;
    }
}