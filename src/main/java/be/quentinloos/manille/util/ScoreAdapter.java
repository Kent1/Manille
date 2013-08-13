package be.quentinloos.manille.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import be.quentinloos.manille.R;

/**
 * Adapter for scores in listview
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public class ScoreAdapter extends ArrayAdapter<int[]> {

    private final List<int[]> turns;
    private final LayoutInflater inflater;

    public ScoreAdapter(Context context, List<int[]> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.turns = objects;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_turn, null);

            holder.scoreteam1 = (TextView)convertView.findViewById(R.id.scoreteam1);
            holder.scoreteam2 = (TextView)convertView.findViewById(R.id.scoreteam2);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.scoreteam1.setText(Integer.toString(turns.get(position)[0]));
        holder.scoreteam2.setText(Integer.toString(turns.get(position)[1]));

        return convertView;
    }

    private class ViewHolder {
        TextView scoreteam1;
        TextView scoreteam2;
    }
}
