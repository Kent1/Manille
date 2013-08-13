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
    private List<int[]> turns;
    /** Multiplying factor for the current turn */
    private int mult;

    public Manille() {
        score = new int[] { 0, 0 };
        turns = new ArrayList<int[]>();
        mult = 1;
    }

    public int[] getScore() {
        return score;
    }

    public List<int[]> getTurns() {
        return turns;
    }

    public int getNbrTurns() {
        return turns.size();
    }

    /**
     * End of a turn. Computes the score of the teams and increments the number of turns.
     *
     * @param points1 Team1's points
     * @param points2 Team2's points
     * @param double1 True if team1 have decided to double
     * @param double2 True if team2 have decided to double
     */
    public void endTurns(int points1, int points2, boolean double1, boolean double2) {
        if (points1 < 0 || points1 > 60
                || points2 < 0 || points2 > 60
                || points1 + points2 != 60)
            throw new IllegalArgumentException("Bad number of points");

        if(double1)
            mult += 1;
        if(double2)
            mult += 1;

        if (points1 == points2) {
            mult +=1;
            turns.add(new int[] { 0, 0 });
        }
        else {
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
