package net.filipvanlaenen.iacaj;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.filipvanlaenen.iacaj.BooleanConstraint.BooleanEqualityConstraint;
import net.filipvanlaenen.iacaj.BooleanConstraint.BooleanFalseConstraint;
import net.filipvanlaenen.iacaj.BooleanConstraint.BooleanOppositionConstraint;
import net.filipvanlaenen.iacaj.BooleanConstraint.BooleanTrueConstraint;
import net.filipvanlaenen.iacaj.BooleanFunction.BooleanExpressionComparator;

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
        normalize();
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
     * Returns a new BooleanConstraints instance based on this instance, extended with a new Boolean constraint.
     *
     * @param inputParameter The input parameter for the new Boolean constraint.
     * @param rightHandSide  The right hand side of the new Boolean constraint.
     * @return A new BooleanConstraints instance based on the set of constraints of this instance, extended with a new
     *         Boolean constraint.
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

    /**
     * Returns the constraints in sorted order.
     *
     * @return A sorted list with the constraints.
     */
    List<BooleanConstraint> getSortedConstraints() {
        List<BooleanConstraint> sortedConstraints = new ArrayList<BooleanConstraint>(constraints);
        sortedConstraints.sort(new BooleanExpressionComparator());
        return sortedConstraints;
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (BooleanConstraint constraint : constraints) {
            // EQMU: Replacing integer addition with subtraction produces an equivalent mutant.
            result += constraint.hashCode();
        }
        return result;
    }

    /**
     * Normalizes the constraint, such that chains of equality and opposition are resolved to equality and opposition to
     * the lowest input parameter.
     */
    private void normalize() {
        BooleanConstraint constraintA = null;
        BooleanConstraint constraintB = null;
        do {
            constraintA = null;
            for (BooleanConstraint constraint : constraints) {
                if (constraint instanceof BooleanEqualityConstraint) {
                    BooleanEqualityConstraint bec = (BooleanEqualityConstraint) constraint;
                    InputParameter ip = bec.getInputParameters().getAt(0);
                    for (BooleanConstraint other : constraints) {
                        if (other != constraint && other.getName().equals(ip.getName())) {
                            constraintA = constraint;
                            constraintB = other;
                        }
                    }
                } else if (constraint instanceof BooleanOppositionConstraint) {
                    BooleanOppositionConstraint boc = (BooleanOppositionConstraint) constraint;
                    InputParameter ip = boc.getInputParameters().getAt(0);
                    for (BooleanConstraint other : constraints) {
                        if (other != constraint && other.getName().equals(ip.getName())) {
                            constraintA = constraint;
                            constraintB = other;
                        }
                    }
                }
            }
            if (constraintA != null) {
                if (constraintA instanceof BooleanEqualityConstraint) {
                    BooleanEqualityConstraint a = (BooleanEqualityConstraint) constraintA;
                    if (constraintB instanceof BooleanEqualityConstraint) {
                        BooleanEqualityConstraint b = (BooleanEqualityConstraint) constraintB;
                        BooleanEqualityConstraint r =
                                new BooleanEqualityConstraint(a.getName(), b.getInputParameters().getAt(0).getName());
                        constraints.remove(constraintA);
                        constraints.add(r);
                    } else if (constraintB instanceof BooleanOppositionConstraint) {
                        BooleanOppositionConstraint b = (BooleanOppositionConstraint) constraintB;
                        BooleanOppositionConstraint r =
                                new BooleanOppositionConstraint(a.getName(), b.getInputParameters().getAt(0).getName());
                        constraints.remove(constraintA);
                        constraints.add(r);
                    } else if (constraintB instanceof BooleanTrueConstraint) {
                        BooleanTrueConstraint r = new BooleanTrueConstraint(a.getName());
                        constraints.remove(constraintA);
                        constraints.add(r);
                    } else if (constraintB instanceof BooleanFalseConstraint) {
                        BooleanFalseConstraint r = new BooleanFalseConstraint(a.getName());
                        constraints.remove(constraintA);
                        constraints.add(r);
                    }
                } else if (constraintA instanceof BooleanOppositionConstraint) {
                    BooleanOppositionConstraint a = (BooleanOppositionConstraint) constraintA;
                    if (constraintB instanceof BooleanEqualityConstraint) {
                        BooleanEqualityConstraint b = (BooleanEqualityConstraint) constraintB;
                        BooleanOppositionConstraint r =
                                new BooleanOppositionConstraint(a.getName(), b.getInputParameters().getAt(0).getName());
                        constraints.remove(constraintA);
                        constraints.add(r);
                    } else if (constraintB instanceof BooleanOppositionConstraint) {
                        BooleanOppositionConstraint b = (BooleanOppositionConstraint) constraintB;
                        BooleanEqualityConstraint r =
                                new BooleanEqualityConstraint(a.getName(), b.getInputParameters().getAt(0).getName());
                        constraints.remove(constraintA);
                        constraints.add(r);
                    } else if (constraintB instanceof BooleanTrueConstraint) {
                        BooleanFalseConstraint r = new BooleanFalseConstraint(a.getName());
                        constraints.remove(constraintA);
                        constraints.add(r);
                    } else if (constraintB instanceof BooleanFalseConstraint) {
                        BooleanTrueConstraint r = new BooleanTrueConstraint(a.getName());
                        constraints.remove(constraintA);
                        constraints.add(r);
                    }
                }
            }
        } while (constraintA != null);
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
