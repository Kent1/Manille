package be.quentinloos.manille.gui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
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
import be.quentinloos.manille.gui.dialogs.GameOverDialog;
import be.quentinloos.manille.util.TurnAdapter;

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
    public void onPrepareOptionsMenu(Menu menu) {
        if (((MainActivity) getActivity()).getManille().isEnded()) {
            menu.getItem(0).setVisible(false);
        }
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
           case R.id.action_remove:
               ((MainActivity) getActivity()).getManille().removeLastHand();
               refresh();
               ActivityCompat.invalidateOptionsMenu(getActivity());
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

        pointsTeam1 = (TextView) view.findViewById(R.id.points_team1);
        pointsTeam2 = (TextView) view.findViewById(R.id.points_team2);

        lv = (ListView) view.findViewById(R.id.listView);

        refresh();

        return view;
    }

    public void refresh() {
        Manille manille = ((MainActivity) getActivity()).getManille();

        title.setText(getTitle(manille));

        team1.setText(preferences.getString("team1", getString(R.string.default_team1)));
        team2.setText(preferences.getString("team2", getString(R.string.default_team2)));

        TurnAdapter adapter = new TurnAdapter(getActivity(), manille.getTurns());
        lv.setAdapter(adapter);

        pointsTeam1.setText(Integer.toString(manille.getScore()[0]));
        pointsTeam2.setText(Integer.toString(manille.getScore()[1]));

        if(manille.isEnded()) {
            int winner = manille.getWinner();
            String teamWinner = "";
            if (winner == 1)
                teamWinner = preferences.getString("team1", getString(R.string.default_team1));
            else if (winner == 2)
                teamWinner = preferences.getString("team2", getString(R.string.default_team2));

            DialogFragment gameOverDialog = GameOverDialog.newInstance(teamWinner, manille.getScore()[winner - 1]);
            gameOverDialog.show(getActivity().getSupportFragmentManager(), "game over");
            ActivityCompat.invalidateOptionsMenu(getActivity());
        }
    }

    private String getTitle(Manille manille) {
        // Retrieves preferences
        String[] array = getResources().getStringArray(R.array.manille_array);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        array[1] = String.format(array[1], Integer.parseInt(
                preferences.getString("score", getString(R.string.default_score))));
        array[2] = String.format(array[2], Integer.parseInt(
                preferences.getString("turns", getString(R.string.default_turns))));

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
