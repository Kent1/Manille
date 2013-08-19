package be.quentinloos.manille.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import be.quentinloos.manille.core.Manille;
import be.quentinloos.manille.core.ManilleFree;
import be.quentinloos.manille.core.ManilleScore;
import be.quentinloos.manille.core.ManilleTurns;
import be.quentinloos.manille.core.Turn;

/**
 * A parcelable implementation of Turn
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public class TurnParcelable implements Parcelable {

    Turn turn;

    public TurnParcelable(Turn turn) {
        this.turn = turn;
    }

    public TurnParcelable(Parcel source) {
        boolean[] array = new boolean[1];
        source.readBooleanArray(array);
        boolean mult = array[0];
        this.turn = new Turn(source.readInt(), source.readInt(), source.readInt(), Turn.Trump.valueOf(source.readString()), mult);
        turn.setMult(source.readInt());
    }

    public Turn getTurn() {
        return turn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBooleanArray(new boolean[] { turn.getMultMode() });
        dest.writeInt(turn.getPoints1());
        dest.writeInt(turn.getPoints2());
        dest.writeInt(turn.getTeam());
        dest.writeString(turn.getTrump().name());
        dest.writeInt(turn.getMult());
    }

    public static final Parcelable.Creator<TurnParcelable> CREATOR = new Parcelable.Creator<TurnParcelable>() {

        @Override
        public TurnParcelable createFromParcel(Parcel source) {

            return new TurnParcelable(source);
        }

        @Override
        public TurnParcelable[] newArray(int size) {

            return new TurnParcelable[size];
        }
    };
}
