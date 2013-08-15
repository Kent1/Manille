package be.quentinloos.manille.core;

/**
 * A X-points Manille card game.
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public class ManilleScore extends Manille {

    /** points to get to finish the game */
    private int threshold;

    public ManilleScore(int threshold) {
        super();
        this.threshold = threshold;
    }

    @Override
    public boolean isEnded() {
        return getScore()[0] >= threshold || getScore()[1] >= threshold;
    }
}
