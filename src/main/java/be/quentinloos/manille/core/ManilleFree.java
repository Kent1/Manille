package be.quentinloos.manille.core;

/**
 * A free Manille card game.
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public class ManilleFree extends Manille {

    public ManilleFree() {
        super();
    }

    @Override
    public boolean isEnded() {
        return false;
    }
}
