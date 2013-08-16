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

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("");
        if (getEnding() > 1)
            str.append(String.format("Manille à %d points", getEnding()));
        else
            str.append(String.format("Manille à %d point", getEnding()));
        if(getNbrTurns() > 1)
            str.append(String.format(" - %d donnes", getNbrTurns()));
        else
            str.append(String.format(" - %d donne", getNbrTurns()));
        return str.toString();
    }
}
