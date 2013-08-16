package be.quentinloos.manille.gui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import be.quentinloos.manille.R;
import be.quentinloos.manille.gui.activities.MainActivity;

/**
 * A dialog to add a turn
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public class AddTurnDialog extends DialogFragment {

    EditText pointsTeam1, pointsTeam2;

    public static AddTurnDialog newInstance(int title) {
        AddTurnDialog dialog = new AddTurnDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_add_turn, null);

        // EditText for points of the turn
        pointsTeam1 = (EditText) view.findViewById(R.id.turn_score1);
        pointsTeam2 = (EditText) view.findViewById(R.id.turn_score2);

        // Listener to autocomplete points
        pointsTeam1.addTextChangedListener(getTextWatcher(pointsTeam1, pointsTeam2));
        pointsTeam2.addTextChangedListener(getTextWatcher(pointsTeam2, pointsTeam1));

        return new AlertDialog.Builder(this.getActivity())
                .setTitle(title)
                .setView(view)

                // Listener for the 'OK' button
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
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
                        try {
                            ((MainActivity) getActivity()).getManille().endTurns(score1, score2, double1, double2);
                        } catch (IllegalArgumentException e) {
                            Toast.makeText(getActivity(), getString(R.string.exception_score), Toast.LENGTH_SHORT).show();
                        }
                        ((MainActivity) getActivity()).refreshMainFragment();
                    }
                })

                // Listener for the 'Cancel' button
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        AddTurnDialog.this.getDialog().cancel();
                    }
                })

                .create();
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