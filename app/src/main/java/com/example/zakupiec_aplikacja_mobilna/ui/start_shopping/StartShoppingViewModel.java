package com.example.zakupiec_aplikacja_mobilna.ui.start_shopping;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StartShoppingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StartShoppingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("START ZAKUPY");
    }

    public LiveData<String> getText() {
        return mText;
    }
}