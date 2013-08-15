package be.quentinloos.manille.gui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import be.quentinloos.manille.R;
import be.quentinloos.manille.core.Manille;
import be.quentinloos.manille.core.ManilleFree;
import be.quentinloos.manille.core.ManilleScore;
import be.quentinloos.manille.core.ManilleTurns;
import be.quentinloos.manille.gui.dialogs.AddTurnDialog;
import be.quentinloos.manille.gui.dialogs.NewManilleDialog;

/**
 * Main Activity
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public class MainActivity extends FragmentActivity implements NewManilleDialog.NoticeDialogListener, AddTurnDialog.NoticeDialogListener {

    private static final int RESULT_SETTINGS = 1;

    private Manille manille;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        manille = new ManilleFree();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                DialogFragment turnDialog = AddTurnDialog.newInstance(R.string.action_add);
                turnDialog.show(getSupportFragmentManager(), "add a turn");
                return true;
            case R.id.action_new:
                DialogFragment manilleDialog = NewManilleDialog.newInstance(R.string.pick_a_type);
                manilleDialog.show(getSupportFragmentManager(), "type");
                return true;
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

                break;
        }
    }

    public Manille getManille() {
        return manille;
    }

    @Override
    public void onDialogManilleFreeClick(DialogFragment dialog) {
        manille = new ManilleFree();
    }

    @Override
    public void onDialogManilleScoreClick(DialogFragment dialog) {
        manille = new ManilleScore(Integer.parseInt(preferences.getString("score", getString(R.string.score_limit))));
    }

    @Override
    public void onDialogManilleTurnsClick(DialogFragment dialog) {
        manille = new ManilleTurns(Integer.parseInt(preferences.getString("turns", getString(R.string.turn_limit))));
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, int score1, int score2, boolean double1, boolean double2) {
        try {
            manille.endTurns(score1, score2, double1, double2);
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, getString(R.string.exception_score), Toast.LENGTH_SHORT).show();
        }
    }
}
