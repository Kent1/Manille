package be.quentinloos.manille.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import be.quentinloos.manille.R;
import be.quentinloos.manille.core.Turn;

/**
 * Adapter for turns in listview
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public class TurnAdapter extends ArrayAdapter<Turn> {

    public TurnAdapter(Context context, List<Turn> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_turn, null);

            holder.symbol  = (TextView) convertView.findViewById(R.id.symbol);
            holder.points1 = (TextView) convertView.findViewById(R.id.scoreteam1);
            holder.points2 = (TextView) convertView.findViewById(R.id.scoreteam2);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.symbol.setText(getItem(position).getTrump().toString());
        if (getItem(position).getTrump() == Turn.Trump.DIAMONDS || getItem(position).getTrump() == Turn.Trump.HEARTS)
            holder.symbol.setTextColor(getContext().getResources().getColor(android.R.color.holo_red_dark));
        holder.points1.setText(Integer.toString(getItem(position).getPoints1()));
        holder.points2.setText(Integer.toString(getItem(position).getPoints2()));

        return convertView;
    }

    private class ViewHolder {
        TextView symbol;
        TextView points1;
        TextView points2;
    }
}
