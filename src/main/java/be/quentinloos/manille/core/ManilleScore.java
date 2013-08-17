package be.quentinloos.manille.core;

/**
 * A X-points Manille card game.
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public class ManilleScore extends Manille {

    /** points to get to finish the game */
    private int ending;

    public ManilleScore(int ending) {
        super();
        this.ending = ending;
    }

    public int getEnding() {
        return ending;
    }

    @Override
    public boolean isEnded() {
        return getScore()[0] >= ending || getScore()[1] >= ending;
    }
}
