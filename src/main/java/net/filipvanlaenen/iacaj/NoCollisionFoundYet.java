package net.filipvanlaenen.iacaj;

import java.util.List;
import java.util.stream.Collectors;

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
     * The Boolean constraints with the best result so far.
     */
    private final BooleanConstraints bestBooleanConstraints;
    private final long bestNumberOfExpressions;

    /**
     * Constructor taking the number of iterations as a parameter and the set of
     * constraints with the best result so far.
     *
     * @param numberOfIterations     The number of iterations done.
     * @param bestBooleanConstraints The Boolean constraints with the best result so
     *                               far.
     */
    NoCollisionFoundYet(final int numberOfIterations, final BooleanConstraints bestBooleanConstraints,
            final long bestNumberOfExpressions) {
        this.numberOfIterations = numberOfIterations;
        this.bestBooleanConstraints = bestBooleanConstraints;
        this.bestNumberOfExpressions = bestNumberOfExpressions;
    }

    /**
     * Returns the number of iterations done.
     *
     * @return The number of iterations done.
     */
    int getNumberOfIterations() {
        return numberOfIterations;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("No collision found in the Boolean function after ");
        if (numberOfIterations == 1) {
            sb.append("1 iteration.\n");
        } else {
            sb.append(numberOfIterations + " iterations.\n");
        }
        sb.append("  Best set of constraints so far:\n");
        List<BooleanConstraint> constraints = bestBooleanConstraints.getSortedConstraints();
        sb.append("    ");
        List<String> constraintLines = constraints.stream().map(BooleanConstraint::toString)
                .collect(Collectors.toList());
        sb.append(String.join("\n    ", constraintLines));
        sb.append("\n");
        sb.append("  Number of expressions: ");
        sb.append(bestNumberOfExpressions);
        return sb.toString();
    }
}
