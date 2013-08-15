package be.quentinloos.manille.core;

/**
 * A X-turns Manille card game.
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public class ManilleTurns extends Manille {

    /** Number of turns in the game */
    private int ending;

    public ManilleTurns(int ending) {
        super();
        this.ending = ending;
    }

    public int getEnding() {
        return ending;
    }

    @Override
    public boolean isEnded() {
        return this.getNbrTurns() >= ending;
    }
}
