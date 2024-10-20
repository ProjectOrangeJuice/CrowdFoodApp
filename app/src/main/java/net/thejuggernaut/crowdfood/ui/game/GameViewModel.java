package net.thejuggernaut.crowdfood.ui.game;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GameViewModel() {

    }

    public LiveData<String> getText() {
        return mText;
    }
}