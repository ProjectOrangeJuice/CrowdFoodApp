package net.thejuggernaut.crowdfood.ui.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import net.thejuggernaut.crowdfood.R;

public class GameFragment extends Fragment {

    private GameViewModel gameViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gameViewModel =
                ViewModelProviders.of(this).get(GameViewModel.class);
        View root = inflater.inflate(R.layout.fragment_game, container, false);

        return root;
    }



}