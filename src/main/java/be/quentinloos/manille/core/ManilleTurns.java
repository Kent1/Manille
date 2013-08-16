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
        this.nbrNoTrump = this.nbrNoTrump1 = this.nbrNoTrump2 = nbrNoTrump;
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

    @Override
    public void endTurns(int points1, int points2, boolean double1, boolean double2, boolean noTrump1, boolean noTrump2) {
        if (noTrump1 && nbrNoTrump1 > 0)
            nbrNoTrump1--;
        else if (noTrump2 && nbrNoTrump2 > 0)
            nbrNoTrump2--;
        super.endTurns(points1, points2, double1, double2, noTrump1, noTrump2);
    }

    @Override
    public boolean isEnded() {
        return this.getNbrTurns() >= ending;
    }
}
