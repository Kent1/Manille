package be.quentinloos.manille.core;

/**
 * A X-turns Manille card game.
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public class ManilleTurns extends Manille {

    /** Number of turns in the game */
    private int ending;
    /** Number of no-trump for team1 */
    private int nbrNoTrump1;
    /** Number of no-trump for team2 */
    private int nbrNoTrump2;

    public ManilleTurns(int ending, int nbrNoTrump) {
        super();
        this.ending = ending;
        this.nbrNoTrump1 = this.nbrNoTrump2 = nbrNoTrump;
    }

    public int getEnding() {
        return ending;
    }

    public void setNoTrump(int team) {
        if(team == 1 && nbrNoTrump1 > 0)
            nbrNoTrump1--;
        else if(team == 2 && nbrNoTrump2 > 0)
            nbrNoTrump2--;
        setNoTrump();
    }

    @Override
    public boolean isEnded() {
        return this.getNbrTurns() >= ending;
    }
}
