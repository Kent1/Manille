package be.quentinloos.manille.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for Manille card game.
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public abstract class Manille {

    /** Current score of the game */
    private int[] score;
    /** List of scores of the differents turns */
    private ArrayList<int[]> turns;
    /** Multiplying factor for the current turn */
    private int mult;
    /** boolean used to store if trump suit is no-trump */
    private boolean noTrump;

    public Manille() {
        score = new int[] { 0, 0 };
        turns = new ArrayList<int[]>();
        mult = 1;
    }

    public int[] getScore() {
        return score;
    }

    public void setScore(int[] score) {
        this.score = score;
    }

    public ArrayList<int[]> getTurns() {
        return turns;
    }

    public void setTurns(ArrayList<int[]> turns) {
        this.turns = turns;
    }

    public int getNbrTurns() {
        return turns.size();
    }

    public int getMult() {
        return mult;
    }

    public void setMult(int mult) {
        this.mult = mult;
    }

    public boolean isNoTrump() {
        return noTrump;
    }

    public void setNoTrump() {
        this.noTrump = true;
    }

    /**
     * End of a turn. Computes the score of the teams and increments the number of turns.
     *
     * @param points1 Team1's points
     * @param points2 Team2's points
     * @param double1 True if team1 have decided to double
     * @param double2 True if team2 have decided to double
     * @param noTrump1 True if the trump suit is no-trump
     * @param noTrump2 True if the trump suit is no-trump
     */
    public void endTurns(int points1, int points2, boolean double1, boolean double2, boolean noTrump1, boolean noTrump2) {
        if (points1 < 0 || points1 > 60
                || points2 < 0 || points2 > 60
                || points1 + points2 != 60)
            throw new IllegalArgumentException("Bad number of points");

        if(double1)
            mult++;
        if(double2)
            mult++;

        if(noTrump1 || noTrump2)
            mult++;

        if (points1 == points2) {
            mult++;
            turns.add(new int[] { 0, 0 });
        }
        else {
            if (points1 == 60 || points2 == 60)
                mult += 1;
            boolean winner = points1 > points2;
            int points = Math.abs((winner ? points1 : points2) - 30) * mult;
            this.score[winner ? 0 : 1] += points;
            turns.add(new int[] { (winner ? points : 0), (winner ? 0 : points) });
            mult = 1;
        }
    }

    /**
     * Is the end of game ?
     * @return true if the game is ended
     */
    public abstract boolean isEnded();
}
