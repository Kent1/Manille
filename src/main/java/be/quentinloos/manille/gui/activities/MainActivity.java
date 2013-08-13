package be.quentinloos.manille.gui.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
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
import be.quentinloos.manille.gui.dialogs.ManilleDialog;
import be.quentinloos.manille.gui.dialogs.TurnDialog;
import be.quentinloos.manille.util.ScoreAdapter;

/**
 * Main Activity
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public class MainActivity extends Activity implements ManilleDialog.NoticeDialogListener, TurnDialog.NoticeDialogListener {

    private static final int RESULT_SETTINGS = 1;

    private Manille manille;
    private ScoreAdapter adapter;
    private SharedPreferences preferences;
    private TextView pointsTeam1;
    private TextView pointsTeam2;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        TextView team1 = (TextView) findViewById(R.id.team1);
        TextView team2 = (TextView) findViewById(R.id.team2);
        team1.setText(preferences.getString("team1", getString(R.string.valueTeam1)));
        team2.setText(preferences.getString("team2", getString(R.string.valueTeam2)));

        manille = new ManilleFree();

        pointsTeam1 = (TextView) findViewById(R.id.points_team_1);
        pointsTeam2 = (TextView) findViewById(R.id.points_team_2);

        lv = (ListView) findViewById(R.id.listView);
        adapter = new ScoreAdapter(this, manille.getTurns());
        lv.setAdapter(adapter);

        refresh();
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
                DialogFragment turnDialog = new TurnDialog();
                turnDialog.show(getFragmentManager(), "turn");
                return true;
            case R.id.action_new:
                DialogFragment manilleDialog = new ManilleDialog();
                manilleDialog.show(getFragmentManager(), "type");
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
                TextView team1 = (TextView) findViewById(R.id.team1);
                TextView team2 = (TextView) findViewById(R.id.team2);
                team1.setText(preferences.getString("team1", getString(R.string.valueTeam1)));
                team2.setText(preferences.getString("team2", getString(R.string.valueTeam2)));
                break;
        }
    }

    private void refresh() {
        adapter.notifyDataSetChanged();
        pointsTeam1.setText(Integer.toString(manille.getScore()[0]));
        pointsTeam2.setText(Integer.toString(manille.getScore()[1]));
    }

    @Override
    public void onDialogManilleFreeClick(DialogFragment dialog) {
        manille = new ManilleFree();
        adapter = new ScoreAdapter(this, manille.getTurns());
        lv.setAdapter(adapter);
        refresh();
    }

    @Override
    public void onDialogManilleScoreClick(DialogFragment dialog) {
        manille = new ManilleScore(Integer.parseInt(preferences.getString("score", getString(R.string.valueScore))));
        adapter = new ScoreAdapter(this, manille.getTurns());
        lv.setAdapter(adapter);
        refresh();
    }

    @Override
    public void onDialogManilleTurnsClick(DialogFragment dialog) {
        manille = new ManilleTurns(Integer.parseInt(preferences.getString("turns", getString(R.string.valueTurns))));
        adapter = new ScoreAdapter(this, manille.getTurns());
        lv.setAdapter(adapter);
        refresh();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, int score1, int score2, boolean double1, boolean double2) {
        try {
            manille.endTurns(score1, score2, double1, double2);
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, getString(R.string.exception_score), Toast.LENGTH_SHORT).show();
        }
        refresh();
    }
}
