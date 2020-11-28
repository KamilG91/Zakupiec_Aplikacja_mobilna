package com.example.zakupiec_aplikacja_mobilna.ui.main_menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.zakupiec_aplikacja_mobilna.R;

public class MainMenuFragment extends Fragment {

    private MainMenuViewModel mainMenuViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainMenuViewModel =
                new ViewModelProvider(this).get(MainMenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_main_menu, container, false);
        Button btn = (Button) root.findViewById(R.id.button_stworz_liste);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_stworz_liste);
            }
        });

        final TextView textView = root.findViewById(R.id.textView_tworcy);
        mainMenuViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}