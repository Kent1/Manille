package be.quentinloos.manille.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for Manille card game.
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public abstract class Manille {

    /** Team's score */
    private int[] scores;
    /** List of scores of the differents turns */
    private List<int[]> turns;
    /** The number of turns played */
    private int nbrTurns;
    /** Multiplying factor for the current turn */
    private int mult;

    public Manille() {
        scores = new int[] { 0, 0 };
        turns = new ArrayList<int[]>();
        nbrTurns = 0;
        mult = 1;
    }

    public int[] getScores() {
        return scores;
    }

    public List<int[]> getTurns() {
        return turns;
    }

    public int getNbrTurns() {
        return nbrTurns;
    }

    /**
     * End of a turn. Computes the score of the teams and increments the number of turns.
     *
     * @param score1 Team1's score
     * @param score2 Team2's score
     */
    public void endTurns(int score1, int score2) {
        assert score1 >= 0 && score1 <= 60;
        assert score2 >= 0 && score2 <= 60;
        assert score1 + score2 == 60;

        turns.add(new int[] { score1, score2 });

        if(score1 == score2)
            mult +=1;
        else {
            this.scores[score1 > score2 ? 0 : 1] += Math.abs(score1 - score2) * mult;
            mult = 1;
        }

        this.nbrTurns++;
    }

    /**
     * Is the end of game ?
     * @return true if the game is ended
     */
    public abstract boolean hasNext();
}
