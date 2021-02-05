package net.filipvanlaenen.iacaj;

/**
 * Attack result when no collision was found yet in a Boolean function after a
 * limited number of iterations.
 */
public final class NoCollisionFoundYet implements AttackResult {
    /**
     * The number of iterations done.
     */
    private final int numberOfIterations;

    /**
     * Constructor taking the number of iterations as a parameter.
     *
     * @param numberOfIterations The number of iterations done.
     */
    public NoCollisionFoundYet(final int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    @Override
    public String toString() {
        if (numberOfIterations == 1) {
            return "No collision found in the Boolean function after 1 iteration.";
        } else {
            return "No collision found in the Boolean function after " + numberOfIterations + " iterations.";
        }
    }
}
