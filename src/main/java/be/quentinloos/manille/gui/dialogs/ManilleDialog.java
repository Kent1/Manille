package be.quentinloos.manille.gui.dialogs;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import be.quentinloos.manille.R;

/**
 * A dialog to choose the type of Manille
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ManilleDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.pick_a_type);
        builder.setItems(R.array.manille_array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // The 'which' argument contains the index position
                // of the selected item
                // 1. ManilleFree
                // 2. ManilleScore
                // 3. ManilleTurns
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