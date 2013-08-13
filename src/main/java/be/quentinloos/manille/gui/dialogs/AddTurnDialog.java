package be.quentinloos.manille.gui.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
public class AddTurnDialog extends DialogFragment {

    EditText pointsTeam1, pointsTeam2;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setTitle(R.string.action_add);
        final View view = inflater.inflate(R.layout.dialog_add_turn, null);
        builder.setView(view);

        // EditText for points of the turn
        pointsTeam1 = (EditText) view.findViewById(R.id.turn_score1);
        pointsTeam2 = (EditText) view.findViewById(R.id.turn_score2);

        // Listener to autocomplete points
        pointsTeam1.addTextChangedListener(getTextWatcher(pointsTeam1, pointsTeam2));
        pointsTeam2.addTextChangedListener(getTextWatcher(pointsTeam2, pointsTeam1));

        // Listener for the 'OK' button
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                int score1 = 0, score2 = 0;
                try {
                    score1 = Integer.parseInt(((EditText) view.findViewById(R.id.turn_score1)).getText().toString());
                    score2 = Integer.parseInt(((EditText) view.findViewById(R.id.turn_score2)).getText().toString());
                } catch (NumberFormatException e) {
                    AddTurnDialog.this.getDialog().cancel();
                    Toast.makeText(view.getContext(), getString(R.string.exception_score), Toast.LENGTH_SHORT).show();
                }
                boolean double1 = ((CheckBox) view.findViewById(R.id.turn_double1)).isChecked();
                boolean double2 = ((CheckBox) view.findViewById(R.id.turn_double2)).isChecked();
                mListener.onDialogPositiveClick(AddTurnDialog.this, score1, score2, double1, double2);
            }
        });

        // Listener for the 'Cancel' button
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                AddTurnDialog.this.getDialog().cancel();
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

    /**
     * This method returns a TextWatcher, a listener for EditText, who listens the first EditText,
     * computes the points of the other team and autocomplete it in the second EditText.
     *
     * @param points1EditText The EditText who have the focus
     * @param points2EditText The EditText to autocomplete
     * @return a configured TextWatcher
     */
    public TextWatcher getTextWatcher(final EditText points1EditText, final EditText points2EditText) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(points1EditText.isFocused()) {
                    try {
                        int points1 = Integer.parseInt(s.toString()), points2;
                        if (points1 < 61) {
                            points2 = 60 - points1;
                            points2EditText.setText(String.valueOf(points2));
                        }
                    } catch (NumberFormatException e) {
                        points2EditText.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
    }
}