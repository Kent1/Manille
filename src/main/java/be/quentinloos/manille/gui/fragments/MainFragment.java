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
import be.quentinloos.manille.core.ManilleFree;
import be.quentinloos.manille.core.ManilleScore;
import be.quentinloos.manille.core.ManilleTurns;
import be.quentinloos.manille.gui.activities.MainActivity;
import be.quentinloos.manille.gui.dialogs.AddTurnDialog;
import be.quentinloos.manille.util.ScoreAdapter;

/**
 * Main Fragment
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public class MainFragment extends Fragment {

    private TextView title;
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

        title = (TextView) view.findViewById(R.id.title);

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

        title.setText(getTitle(manille));

        team1.setText(preferences.getString("team1", getString(R.string.name_team_1)));
        team2.setText(preferences.getString("team2", getString(R.string.name_team_2)));

        ScoreAdapter adapter = new ScoreAdapter(getActivity(), manille.getTurns());
        lv.setAdapter(adapter);

        pointsTeam1.setText(Integer.toString(manille.getScore()[0]));
        pointsTeam2.setText(Integer.toString(manille.getScore()[1]));
    }

    private String getTitle(Manille manille) {
        // Retrieves preferences
        String[] array = getResources().getStringArray(R.array.manille_array);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        array[1] = String.format(array[1], Integer.parseInt(
                preferences.getString("score", getString(R.string.score_limit))));
        array[2] = String.format(array[2], Integer.parseInt(
                preferences.getString("turns", getString(R.string.turn_limit))));

        // Constructs a String with the correct type of Manille
        StringBuilder str = new StringBuilder("");
        if(manille instanceof ManilleFree) {
            str.append(array[0]);
        }
        else if(manille instanceof ManilleScore)
            str.append(array[1]);
        else if(manille instanceof ManilleTurns)
            str.append(array[2]);

        str.append(" - ");

        // Display the number of hands played
        if (manille.getNbrTurns() ==0)
            str.append(getString(R.string.zero_turns));
        else
            str.append(getResources().getQuantityString(
                    R.plurals.number_of_turns, manille.getNbrTurns(), manille.getNbrTurns()));

        // Display number of nutrump plays and notrump to do
        if (manille instanceof ManilleTurns) {
            str.append("\n");
            str.append(String.format(getString(R.string.number_of_notrump),
                    ((ManilleTurns) manille).getNbrNoTrump1(),
                    ((ManilleTurns) manille).getNbrNoTrump()));
            str.append(" - ");
            str.append(String.format(getString(R.string.number_of_notrump),
                    ((ManilleTurns) manille).getNbrNoTrump2(),
                    ((ManilleTurns) manille).getNbrNoTrump()));
        }

        return str.toString();
    }
}
