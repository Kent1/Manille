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

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Manille libre");
        if(getNbrTurns() > 1)
            str.append(String.format(" - %d donnes", getNbrTurns()));
        else
            str.append(String.format(" - %d donne", getNbrTurns()));
        return str.toString();
    }
}
