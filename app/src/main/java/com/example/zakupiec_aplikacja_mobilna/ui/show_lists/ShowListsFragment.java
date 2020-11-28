package com.example.zakupiec_aplikacja_mobilna.ui.show_lists;

import android.app.Activity;
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

public class ShowListsFragment extends Fragment {

    private ShowListsViewModel showListsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        showListsViewModel =
                new ViewModelProvider(this).get(ShowListsViewModel.class);
        View root = inflater.inflate(R.layout.listy_zakupow, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        showListsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}