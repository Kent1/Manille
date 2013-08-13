package be.quentinloos.manille.activities;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

import be.quentinloos.manille.R;
import be.quentinloos.manille.core.Manille;
import be.quentinloos.manille.core.ManilleFree;
import be.quentinloos.manille.util.ScoreAdapter;

/**
 * Main Activity
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Manille manille = new ManilleFree();
        manille.endTurns(25, 35);

        ListView lv = (ListView) findViewById(R.id.listView);
        ScoreAdapter sa = new ScoreAdapter(this, manille.getTurns());
        lv.setAdapter(sa);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
