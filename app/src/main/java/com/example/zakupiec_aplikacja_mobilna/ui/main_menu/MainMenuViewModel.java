package com.example.zakupiec_aplikacja_mobilna.ui.main_menu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainMenuViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MainMenuViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}