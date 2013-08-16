package be.quentinloos.manille.core;

/**
 * This class represents a turn (a hand) in a Manille game
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public class Turn {

    public enum Trump {
        CLUBS,    // Trèfles
        DIAMONDS, // Carreaux
        HEARTS,   // Coeurs
        SPADES,   // Piques
        NOTRUMP   // Sans atout
    }

    private int points1, points2;
    private Trump trump;
    private int mult;

    /**
     * Constructs a new Turn.
     *
     * @param points1 team1's points.
     * @param points2 team2's points.
     * @param trump The trump chosen.
     * @param double1 If the team1 have doubled ?
     * @param double2 If the team2 have doubled ?
     * @param drawInLastPlay Number of draw play before.
     */
    public Turn(int points1, int points2, Trump trump, boolean double1, boolean double2, int drawInLastPlay) {
        if (points1 < 0 || points1 > 60 || points2 < 0 || points2 > 60 || points1 + points2 != 60)
            throw new IllegalArgumentException("Bad number of points");

        this.mult = computeMult(points1, points2, trump, double1, double2, drawInLastPlay);

        this.trump = trump;

        this.points1 = (points1 > 29) ? (points1 - 30) * mult : 0;
        this.points2 = (points2 > 29) ? (points2 - 30) * mult : 0;
    }

    /**
     * Computes the factor of multiplication for the turn.
     *
     * @return The factor of multiplication
     */
    private int computeMult(int points1, int points2, Trump trump, boolean double1, boolean double2, int drawInLastPlay) {
        int mult = 0;

        if (points1 == 60 || points2 == 60)
            mult++;

        if (trump == Trump.NOTRUMP)
            mult++;

        if (double1) mult++;
        if (double2) mult++;

        mult += drawInLastPlay;

        return mult;
    }

    public Turn(int points1, int points2, Trump trump) {
        this(points1, points2, trump, false, false, 0);
    }

    public int getPoints1() {
        return points1;
    }

    public void setPoints1(int points1) {
        this.points1 = points1;
    }

    public int getPoints2() {
        return points2;
    }

    public void setPoints2(int points2) {
        this.points2 = points2;
    }

    public Trump getTrump() {
        return trump;
    }

    public void setTrump(Trump trump) {
        this.trump = trump;
    }

    public int getMult() {
        return mult;
    }
}