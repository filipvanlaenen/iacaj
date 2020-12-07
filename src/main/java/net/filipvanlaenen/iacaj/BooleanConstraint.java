package net.filipvanlaenen.iacaj;

import java.util.HashSet;
import java.util.Set;

/**
 * Class representing a Boolean constraint.
 */
public class BooleanConstraint extends BooleanExpression {
    private static class BooleanTrueConstraint extends BooleanConstraint {
        public BooleanTrueConstraint(final String name) {
            super(name);
        }

        @Override
        public String toString() {
            return name + " = True";
        }
    }

    private static class BooleanFalseConstraint extends BooleanConstraint {
        public BooleanFalseConstraint(final String name) {
            super(name);
        }

        @Override
        public String toString() {
            return name + " = False";
        }
    }

    private static class BooleanEqualityConstraint extends BooleanConstraint {
        /**
         * The name of the other input parameter.
         */
        private final String otherInputParameter;

        public BooleanEqualityConstraint(final String name, final String otherName) {
            super(name);
            this.otherInputParameter = otherName;
            inputParameters.add(InputParameter.get(otherName));
        }

        @Override
        public String toString() {
            return name + " = " + otherInputParameter;
        }
    }

    private static class BooleanNegationConstraint extends BooleanConstraint {
        /**
         * The name of the other input parameter.
         */
        private final String otherInputParameter;

        public BooleanNegationConstraint(final String name, final String otherName) {
            super(name);
            this.otherInputParameter = otherName;
            inputParameters.add(InputParameter.get(otherName));
        }

        @Override
        public String toString() {
            return name + " = ¬" + otherInputParameter;
        }
    }

    /**
     * The input parameters used in the Boolean constraint.
     */
    Set<InputParameter> inputParameters = new HashSet<InputParameter>();
    /**
     * The name of the constraint.
     */
    final String name;

    /**
     * Creates a Boolean constraint based on the left hand side and the right hand
     * side of the expression.
     *
     * @param leftHandSide  The left hand side of the constraint.
     * @param rightHandSide The right hand side of the constraint.
     */
    public static BooleanConstraint parse(final String leftHandSide, final String rightHandSide) {
        if ("True".equals(rightHandSide)) {
            return new BooleanTrueConstraint(leftHandSide);
        } else if ("False".equals(rightHandSide)) {
            return new BooleanFalseConstraint(leftHandSide);
        } else if (rightHandSide.startsWith("¬")) {
            return new BooleanNegationConstraint(leftHandSide, rightHandSide.substring(1));
        } else {
            return new BooleanEqualityConstraint(leftHandSide, rightHandSide);
        }
    }

    public BooleanConstraint(String name) {
        this.name = name;
    }

    @Override
    public Set<InputParameter> getInputParameters() {
        return inputParameters;
    }

    public String getName() {
        return name;
    }

}
