package be.quentinloos.manille.core;

import android.util.Log;

/**
 * A X-turns Manille card game.
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public class ManilleTurns extends Manille {

    /** Number of turns in the game */
    private int ending;
    /** Number of no-trump per team */
    private int nbrNoTrump;
    /** Number of no-trump remaining for team1 */
    private int nbrNoTrump1;
    /** Number of no-trump remaining for team2 */
    private int nbrNoTrump2;

    public ManilleTurns(int ending, int nbrNoTrump) {
        super();
        this.ending = ending;
        this.nbrNoTrump = nbrNoTrump;
        this.nbrNoTrump1 = this.nbrNoTrump2 = 0;
    }

    public int getEnding() {
        return ending;
    }

    public int getNbrNoTrump() {
        return nbrNoTrump;
    }

    public int getNbrNoTrump1() {
        return nbrNoTrump1;
    }

    public void setNbrNoTrump1(int nbrNoTrump1) {
        this.nbrNoTrump1 = nbrNoTrump1;
    }

    public int getNbrNoTrump2() {
        return nbrNoTrump2;
    }

    public void setNbrNoTrump2(int nbrNoTrump2) {
        this.nbrNoTrump2 = nbrNoTrump2;
    }

    public void addNoTrumpTurn(int team) {
        if (team == 1 && nbrNoTrump1 < getNbrNoTrump())
            nbrNoTrump1++;
        else if (team == 2 && nbrNoTrump2 < getNbrNoTrump())
            nbrNoTrump2++;
    }

    @Override
    public boolean isEnded() {
        return this.getNbrTurns() >= ending;
    }
}
