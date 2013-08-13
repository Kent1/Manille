package be.quentinloos.manille.gui.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import be.quentinloos.manille.R;

/**
 * A dialog to choose the type of Manille
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public class ManilleDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.pick_a_type);
        String[] array = getResources().getStringArray(R.array.manille_array);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        array[1] = String.format(array[1], Integer.parseInt(preferences.getString("score", getString(R.string.score_limit))));
        array[2] = String.format(array[2], Integer.parseInt(preferences.getString("turns", getString(R.string.turn_limit))));
        builder.setItems(array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // The 'which' argument contains the index position
                // of the selected item
                // 0. ManilleFree
                // 1. ManilleScore
                // 2. ManilleTurns
                switch (which) {
                    case 0:
                        mListener.onDialogManilleFreeClick(ManilleDialog.this);
                        break;

                    case 1:
                        mListener.onDialogManilleScoreClick(ManilleDialog.this);
                        break;

                    case 2:
                        mListener.onDialogManilleTurnsClick(ManilleDialog.this);
                        break;
                }
            }
        });
        return builder.create();
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