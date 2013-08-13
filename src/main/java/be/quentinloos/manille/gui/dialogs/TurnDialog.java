package be.quentinloos.manille.gui.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import be.quentinloos.manille.R;

/**
 * A dialog to add a turn
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public class TurnDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setTitle(R.string.action_add);
        final View view = inflater.inflate(R.layout.dialog_add_turn, null);
        builder.setView(view);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                int score1 = 0, score2 = 0;
                try {
                    score1 = Integer.parseInt(((EditText) view.findViewById(R.id.turn_score1)).getText().toString());
                    score2 = Integer.parseInt(((EditText) view.findViewById(R.id.turn_score2)).getText().toString());
                } catch (NumberFormatException e) {
                    TurnDialog.this.getDialog().cancel();
                    Toast.makeText(view.getContext(), getString(R.string.exception_score), Toast.LENGTH_SHORT).show();
                }
                boolean double1 = ((CheckBox) view.findViewById(R.id.turn_double1)).isChecked();
                boolean double2 = ((CheckBox) view.findViewById(R.id.turn_double2)).isChecked();
                mListener.onDialogPositiveClick(TurnDialog.this, score1, score2, double1, double2);
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                TurnDialog.this.getDialog().cancel();
            }
        });

        return builder.create();
    }


    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, int score1, int score2, boolean double1, boolean double2);
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