package be.quentinloos.manille.gui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import be.quentinloos.manille.R;
import be.quentinloos.manille.core.Manille;
import be.quentinloos.manille.gui.activities.MainActivity;
import be.quentinloos.manille.gui.dialogs.AddTurnDialog;
import be.quentinloos.manille.util.ScoreAdapter;

/**
 * Main Fragment
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public class MainFragment extends Fragment {

    private TextView team1, team2;
    private SharedPreferences preferences;
    private TextView pointsTeam1, pointsTeam2;
    private ListView lv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       inflater.inflate(R.menu.fragment_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       // handle item selection
       switch (item.getItemId()) {
           case R.id.action_add:
               DialogFragment turnDialog = AddTurnDialog.newInstance(R.string.action_add);
               turnDialog.show(getActivity().getSupportFragmentManager(), "add a turn");
               return true;
          default:
             return super.onOptionsItemSelected(item);
       }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        team1 = (TextView) view.findViewById(R.id.team1);
        team2 = (TextView) view.findViewById(R.id.team2);

        pointsTeam1 = (TextView) view.findViewById(R.id.points_team_1);
        pointsTeam2 = (TextView) view.findViewById(R.id.points_team_2);

        lv = (ListView) view.findViewById(R.id.listView);

        refresh();

        return view;
    }

    public void refresh() {
        Manille manille = ((MainActivity) getActivity()).getManille();

        team1.setText(preferences.getString("team1", getString(R.string.name_team_1)));
        team2.setText(preferences.getString("team2", getString(R.string.name_team_2)));

        ScoreAdapter adapter = new ScoreAdapter(getActivity(), manille.getTurns());
        lv.setAdapter(adapter);

        pointsTeam1.setText(Integer.toString(manille.getScore()[0]));
        pointsTeam2.setText(Integer.toString(manille.getScore()[1]));
    }
}
