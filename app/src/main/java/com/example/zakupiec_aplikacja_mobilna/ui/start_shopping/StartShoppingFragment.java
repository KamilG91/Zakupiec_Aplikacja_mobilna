package com.example.zakupiec_aplikacja_mobilna.ui.start_shopping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.zakupiec_aplikacja_mobilna.R;

public class StartShoppingFragment extends Fragment {

    private StartShoppingViewModel startShoppingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        startShoppingViewModel =
                new ViewModelProvider(this).get(StartShoppingViewModel.class);
        View root = inflater.inflate(R.layout.stworz_liste, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        startShoppingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}