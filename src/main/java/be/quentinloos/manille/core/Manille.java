package be.quentinloos.manille.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract class for Manille card game.
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public abstract class Manille {

    /** Current score of the game */
    private int[] score;
    /** List of turns */
    private ArrayList<Turn> turns;

    public Manille() {
        score = new int[] { 0, 0 };
        turns = new ArrayList<Turn>();
    }

    public int[] getScore() {
        return score;
    }

    public void setScore(int[] score) {
        this.score = score;
    }

    public ArrayList<Turn> getTurns() {
        return turns;
    }

    public void setTurns(ArrayList<Turn> turns) {
        this.turns = turns;
    }

    public int getNbrTurns() {
        return turns.size();
    }

    /**
     * Add a turn and computes the new score.
     *
     * @param turn The turn to add.
     */
    public void addTurn(Turn turn) {
        turns.add(turn);

        score[0] += turn.getPoints1();
        score[1] += turn.getPoints2();
    }

    /**
     * Is the end of game ?
     * @return true if the game is ended
     */
    public abstract boolean isEnded();

    @Override
    public abstract String toString();
}
