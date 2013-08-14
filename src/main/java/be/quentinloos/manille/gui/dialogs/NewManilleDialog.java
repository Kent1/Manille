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
                        // The 'which' argument contains the index position
                        // of the selected item
                        // 0. ManilleFree
                        // 1. ManilleScore
                        // 2. ManilleTurns
                        switch (which) {
                            case 0:
                                mListener.onDialogManilleFreeClick(NewManilleDialog.this);
                                break;

                            case 1:
                                mListener.onDialogManilleScoreClick(NewManilleDialog.this);
                                break;

                            case 2:
                                mListener.onDialogManilleTurnsClick(NewManilleDialog.this);
                                break;
                        }
                    }
                })
                .create();
    }

    public interface NoticeDialogListener {
        public void onDialogManilleFreeClick(DialogFragment dialog);
        public void onDialogManilleScoreClick(DialogFragment dialog);
        public void onDialogManilleTurnsClick(DialogFragment dialog);
    }

    NoticeDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

}