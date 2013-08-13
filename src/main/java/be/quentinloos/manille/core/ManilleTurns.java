package be.quentinloos.manille.core;

/**
 * A X-turns Manille card game.
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public class ManilleTurns extends Manille {

    /** Number of turns in the game */
    private int nbrTurnsLimit;

    public ManilleTurns(int nbrTurnsLimit) {
        super();
        this.nbrTurnsLimit = nbrTurnsLimit;
    }

    @Override
    public boolean isEnded() {
        return this.getNbrTurns() >= nbrTurnsLimit;
    }
}
