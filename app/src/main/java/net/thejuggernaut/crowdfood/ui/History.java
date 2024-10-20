package net.thejuggernaut.crowdfood.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import net.thejuggernaut.crowdfood.ui.ui.history.HistoryFragment;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, HistoryFragment.newInstance())
                    .commitNow();
        }
    }
}
