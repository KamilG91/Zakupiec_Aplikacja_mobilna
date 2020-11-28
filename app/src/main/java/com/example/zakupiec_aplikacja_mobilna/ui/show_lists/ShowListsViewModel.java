package com.example.zakupiec_aplikacja_mobilna.ui.show_lists;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShowListsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ShowListsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("THOSE ARE YOUR SHOPPING LISTS");
    }

    public LiveData<String> getText() {
        return mText;
    }
}