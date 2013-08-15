package be.quentinloos.manille.gui.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import be.quentinloos.manille.R;
import be.quentinloos.manille.core.Manille;
import be.quentinloos.manille.core.ManilleFree;
import be.quentinloos.manille.core.ManilleScore;
import be.quentinloos.manille.core.ManilleTurns;
import be.quentinloos.manille.gui.activities.MainActivity;
import be.quentinloos.manille.gui.dialogs.AddTurnDialog;
import be.quentinloos.manille.gui.dialogs.NewManilleDialog;
import be.quentinloos.manille.util.ScoreAdapter;

/**
 * Main Activity
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public class MainFragment extends Fragment {

    private static final int RESULT_SETTINGS = 1;

    private ScoreAdapter adapter;
    private TextView pointsTeam1;
    private TextView pointsTeam2;
    private ListView lv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        TextView team1 = (TextView) view.findViewById(R.id.team1);
        TextView team2 = (TextView) view.findViewById(R.id.team2);
        team1.setText(preferences.getString("team1", getString(R.string.name_team_1)));
        team2.setText(preferences.getString("team2", getString(R.string.name_team_2)));

        pointsTeam1 = (TextView) view.findViewById(R.id.points_team_1);
        pointsTeam2 = (TextView) view.findViewById(R.id.points_team_2);

        lv = (ListView) view.findViewById(R.id.listView);
        Log.i("Manile", "" + (MainActivity) getActivity());
        Log.i("Manile", "" + ((MainActivity) getActivity()).getManille());
        adapter = new ScoreAdapter(view.getContext(), ((MainActivity) getActivity()).getManille().getTurns());
        lv.setAdapter(adapter);

        refresh();

        return view;
    }

    private void refresh() {
        adapter.notifyDataSetChanged();
        pointsTeam1.setText(Integer.toString(((MainActivity) getActivity()).getManille().getScore()[0]));
        pointsTeam2.setText(Integer.toString(((MainActivity) getActivity()).getManille().getScore()[1]));
    }
}
