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

    public ScoreAdapter(Context context, List<int[]> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_turn, null);

            holder.points1 = (TextView)convertView.findViewById(R.id.scoreteam1);
            holder.points2 = (TextView)convertView.findViewById(R.id.scoreteam2);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.points1.setText(Integer.toString(getItem(position)[0]));
        holder.points2.setText(Integer.toString(getItem(position)[1]));

        return convertView;
    }

    private class ViewHolder {
        TextView points1;
        TextView points2;
    }
}
