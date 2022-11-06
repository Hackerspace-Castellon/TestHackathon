package com.example.colibri.ui.friends;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class FriendsViewModel {
    private final MutableLiveData<String> mText;

    public FriendsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
