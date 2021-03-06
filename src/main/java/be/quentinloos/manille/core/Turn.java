package be.quentinloos.manille.core;

/**
 * This class represents a turn (a hand) in a Manille game
 *
 * @author Quentin Loos <contact@quentinloos.be>
 */
public class Turn {

    public enum Trump {
        CLUBS    ("♣"), // Trèfles
        DIAMONDS ("♦"), // Carreaux
        HEARTS   ("♥"), // Coeurs
        SPADES   ("♠"), // Piques
        NOTRUMP  ("×"); // Sans atout

        private final String symbol;

        private Trump(String symbol) {
            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return this.symbol;
        }
    }

    private int points1, points2;
    private Trump trump;
    private int mult;
    private int team;
    private boolean multMode;

    /**
     * Constructs a new Turn.
     *
     * @param points1 team1's points.
     * @param points2 team2's points.
     * @param team the team who choose the trump suit.
     * @param trump The trump chosen.
     * @param double1 If the team1 have doubled ?
     * @param double2 If the team2 have doubled ?
     * @param reportedMult Number of draw play before.
     * @param multMode false if facteur of multiplication must be added, true if it must be multiplied
     */
    public Turn(int points1, int points2, int team, Trump trump, boolean double1, boolean double2, int reportedMult, boolean multMode) {
        if (points1 < 0 || points1 > 60 || points2 < 0 || points2 > 60 || points1 + points2 != 60)
            throw new IllegalArgumentException("Bad number of points");

        computeMult(points1, points2, trump, double1, double2, reportedMult);

        this.team = team;
        this.trump = trump;
        this.multMode = multMode;

        computePoints(points1, points2, multMode);
    }

    public Turn(int points1, int points2, int team, Trump trump, boolean multMode) {
        this(points1, points2, team, trump, false, false, 0, multMode);
    }

    public Turn(int points1, int points2, int team, Trump trump, boolean double1, boolean double2, boolean multMode) {
        this(points1, points2, team, trump, double1, double2, 0, multMode);
    }

    public Turn(int points1, int points2, int team, Trump trump, int reportedMult, boolean multMode) {
        this(points1, points2, team, trump, false, false, reportedMult, multMode);
    }

    /**
     * Computes the factor of multiplication for the turn.
     *
     * @return The factor of multiplication
     */
    private void computeMult(int points1, int points2, Trump trump, boolean double1, boolean double2, int reportedMult) {
        int mult = 1;

        if (points1 == 60 || points2 == 60)
            mult++;

        if (trump == Trump.NOTRUMP)
            mult++;

        if (double1) mult++;
        if (double2) mult++;

        mult += reportedMult;

        this.mult = mult;
    }

    private void computePoints(int points1, int points2, boolean multMode) {
        if(multMode) {
            this.points1 = (points1 > 29) ? (int) ((points1 - 30) * Math.pow(2, mult - 1)) : 0;
            this.points2 = (points2 > 29) ? (int) ((points2 - 30) * Math.pow(2, mult - 1)) : 0;
        } else {
            this.points1 = (points1 > 29) ? (points1 - 30) * mult : 0;
            this.points2 = (points2 > 29) ? (points2 - 30) * mult : 0;
        }
    }

    public int getPoints1() {
        return points1;
    }

    public int getPoints2() {
        return points2;
    }

    public void setPoints(int points1, int points2) {
        if (points1 < 0 || points1 > 60 || points2 < 0 || points2 > 60 || points1 + points2 != 60)
            throw new IllegalArgumentException("Bad number of points");

        this.points1 = points1;
        this.points2 = points2;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
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

    public void setMult(int mult) {
        this.mult = mult;
    }

    public boolean getMultMode() {
        return multMode;
    }

    public boolean isDraw() {
        return this.points1 == this.points2;
    }
}
