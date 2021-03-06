package net.filipvanlaenen.iacaj;

import java.util.HashSet;
import java.util.Set;

/**
 * Class holding a set of Boolean constraints.
 */
public final class BooleanConstraints {
    /**
     * The set of constraints.
     */
    private final Set<BooleanConstraint> constraints;

    /**
     * COnstructor using a set of constraints as the parameter.
     *
     * @param constraints The set with the constraints.
     */
    BooleanConstraints(final Set<BooleanConstraint> constraints) {
        this.constraints = new HashSet<BooleanConstraint>(constraints);
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof BooleanConstraints) {
            BooleanConstraints other = (BooleanConstraints) object;
            if (constraints.size() != other.constraints.size()) {
                return false;
            }
            for (BooleanConstraint constraint : constraints) {
                if (!other.constraints.contains(constraint)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns a new BooleanConstraints instance based on this instance, extended
     * with a new Boolean constraint.
     *
     * @param inputParameter The input parameter for the new Boolean constraint.
     * @param rightHandSide  The right hand side of the new Boolean constraint.
     * @return A new BooleanConstraints instance based on the set of constraints of
     *         this instance, extended with a new Boolean constraint.
     */
    BooleanConstraints extend(final InputParameter inputParameter, final String rightHandSide) {
        Set<BooleanConstraint> extendedConstraints = new HashSet<BooleanConstraint>(constraints);
        extendedConstraints.add((BooleanConstraint) BooleanConstraint.parse(inputParameter.getName(), rightHandSide));
        return new BooleanConstraints(extendedConstraints);
    }

    /**
     * Returns the constraints.
     *
     * @return The constraints.
     */
    Set<BooleanConstraint> getConstraints() {
        return Set.copyOf(constraints);
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (BooleanConstraint constraint : constraints) {
            // Note for mutation testing:
            // Replacing integer addition with subtraction produces an equivalent mutant.
            result += constraint.hashCode();
        }
        return result;
    }

    /**
     * Returns the number of Boolean constraints held by this instance.
     *
     * @return The number of Boolean constraints.
     */
    int size() {
        return constraints.size();
    }
}
