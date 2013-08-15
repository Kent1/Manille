package be.quentinloos.manille.gui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import be.quentinloos.manille.R;
import be.quentinloos.manille.core.Manille;
import be.quentinloos.manille.core.ManilleFree;
import be.quentinloos.manille.core.ManilleScore;
import be.quentinloos.manille.core.ManilleTurns;
import be.quentinloos.manille.gui.dialogs.AddTurnDialog;
import be.quentinloos.manille.gui.dialogs.NewManilleDialog;
import be.quentinloos.manille.gui.fragments.MainFragment;

/**
 * Main Activity
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public class MainActivity extends FragmentActivity {

    private static final int RESULT_SETTINGS = 1;

    private Manille manille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        manille = new ManilleFree();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                this.startActivityForResult(new Intent(this, SettingsActivity.class), RESULT_SETTINGS);
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SETTINGS:
                refreshMainFragment();
                break;
        }
    }

    public Manille getManille() {
        return manille;
    }

    public void newManille(int type) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        // The 'type' argument contains the index position
        // of the selected item
        // 0. ManilleFree
        // 1. ManilleScore
        // 2. ManilleTurns
        switch (type) {
            case 0:
                manille = new ManilleFree();
                break;

            case 1:
                manille = new ManilleScore(Integer.parseInt(preferences.getString("score", getString(R.string.score_limit))));
                break;

            case 2:
                manille = new ManilleTurns(Integer.parseInt(preferences.getString("turns", getString(R.string.turn_limit))));
                break;
        }
        refreshMainFragment();
    }

    public void refreshMainFragment() {
        ((MainFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_main)).refresh();
    }
}
