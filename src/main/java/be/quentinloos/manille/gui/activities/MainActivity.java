package be.quentinloos.manille.gui.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import be.quentinloos.manille.R;
import be.quentinloos.manille.core.Manille;
import be.quentinloos.manille.core.ManilleFree;
import be.quentinloos.manille.gui.dialogs.ManilleDialog;
import be.quentinloos.manille.util.ScoreAdapter;

/**
 * Main Activity
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public class MainActivity extends Activity implements ManilleDialog.NoticeDialogListener{

    Manille manille;
    ScoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manille = new ManilleFree();
        manille.endTurns(25, 35);
        manille.endTurns(25, 35);
        manille.endTurns(25, 35);
        manille.endTurns(25, 35);
        manille.endTurns(25, 35);
        manille.endTurns(25, 35);

        TextView sum1 = (TextView) findViewById(R.id.sum1);
        TextView sum2 = (TextView) findViewById(R.id.sum2);
        sum1.setText(Integer.toString(manille.getScores()[0]));
        sum2.setText(Integer.toString(manille.getScores()[1]));

        ListView lv = (ListView) findViewById(R.id.listView);
        adapter = new ScoreAdapter(this, manille.getTurns());
        lv.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new:
                DialogFragment manilleDialog = new ManilleDialog();
                manilleDialog.show(getFragmentManager(), "Manille");
                return true;
            case R.id.action_settings:
                this.startActivity(new Intent(this, SettingsActivity.class));
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onDialogManilleFreeClick(DialogFragment dialog) {
        manille = new ManilleFree();
        adapter.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDialogManilleScoreClick(DialogFragment dialog) {
        manille = new ManilleFree();
        adapter.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDialogManilleTurnsClick(DialogFragment dialog) {
        manille = new ManilleFree();
        adapter.clear();
        adapter.notifyDataSetChanged();
    }
}
