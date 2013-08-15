package be.quentinloos.manille.gui.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import be.quentinloos.manille.R;
import be.quentinloos.manille.core.ManilleFree;
import be.quentinloos.manille.gui.activities.MainActivity;

/**
 * A dialog to choose the type of Manille
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public class NewManilleDialog extends DialogFragment {

    public static NewManilleDialog newInstance(int title) {
        NewManilleDialog dialog = new NewManilleDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_turn, null);

        String[] array = getResources().getStringArray(R.array.manille_array);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        array[1] = String.format(array[1], Integer.parseInt(preferences.getString("score", getString(R.string.score_limit))));
        array[2] = String.format(array[2], Integer.parseInt(preferences.getString("turns", getString(R.string.turn_limit))));

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setItems(array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((MainActivity) getActivity()).newManille(which);
                    }
                })
                .create();
    }
}