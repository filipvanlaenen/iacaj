package net.filipvanlaenen.iacaj;

public class NoCollisionFoundYet implements AttackResult {

    private final int numberOfIterations;

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
