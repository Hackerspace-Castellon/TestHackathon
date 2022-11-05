package com.example.colibri.ui.mascota;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MascotaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MascotaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is mascota fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}