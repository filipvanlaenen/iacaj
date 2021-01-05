package net.filipvanlaenen.iacaj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class representing a Boolean constraint.
 */
public abstract class BooleanConstraint extends BooleanExpression {
    /**
     * Class representing a Boolean constraint requiring an input parameter to be
     * true.
     */
    private static class BooleanTrueConstraint extends BooleanConstraint {
        /**
         * Constructor using the name of the input parameter as its parameter.
         *
         * @param name The name of the input parameter.
         */
        BooleanTrueConstraint(final String name) {
            super(name);
        }

        @Override
        public List<InputParameter> getInputParameters() {
            return Collections.emptyList();
        }

        @Override
        protected boolean isFalse() {
            return false;
        }

        @Override
        public String toJavaString() {
            return "assert " + getName() + " == true;";
        }

        @Override
        public String toString() {
            return getName() + " = True";
        }
    }

    /**
     * Class representing a Boolean constraint requiring an input parameter to be
     * false.
     */
    private static class BooleanFalseConstraint extends BooleanConstraint {
        /**
         * Constructor using the name of the input parameter as its parameter.
         *
         * @param name The name of the input parameter.
         */
        BooleanFalseConstraint(final String name) {
            super(name);
        }

        @Override
        public List<InputParameter> getInputParameters() {
            return Collections.emptyList();
        }

        @Override
        protected boolean isFalse() {
            return true;
        }

        @Override
        public String toJavaString() {
            return "assert " + getName() + " == false;";
        }

        @Override
        public String toString() {
            return getName() + " = False";
        }
    }

    /**
     * Class representing a Boolean constraint requiring that two input parameters
     * are equal to each other.
     */
    private static class BooleanEqualityConstraint extends BooleanConstraint {
        /**
         * The name of the other input parameter.
         */
        private final String otherInputParameter;
        /**
         * A list with the other input parameter.
         */
        private final List<InputParameter> inputParameterList;

        /**
         * Constructor using the names of the input parameters as its parameters.
         *
         * @param name      The name of the first input parameter.
         * @param otherName The name of the second input parameter.
         */
        BooleanEqualityConstraint(final String name, final String otherName) {
            super(name);
            this.otherInputParameter = otherName;
            inputParameterList = new ArrayList<InputParameter>();
            inputParameterList.add(InputParameter.get(otherName));
        }

        @Override
        public List<InputParameter> getInputParameters() {
            return inputParameterList;
        }

        @Override
        protected boolean isFalse() {
            return false;
        }

        @Override
        public String toJavaString() {
            return "assert " + getName() + " == " + otherInputParameter + ";";
        }

        @Override
        public String toString() {
            return getName() + " = " + otherInputParameter;
        }
    }

    /**
     * Class representing a Boolean constraint requiring that two input parameters
     * are opposite to each other.
     */
    private static class BooleanOppositionConstraint extends BooleanConstraint {
        /**
         * The name of the other input parameter.
         */
        private final String otherInputParameter;
        /**
         * A list with the other input parameter.
         */
        private final List<InputParameter> inputParameterList;

        /**
         * Constructor using the names of the input variables as its parameters.
         *
         * @param name      The name of the first input parameter.
         * @param otherName The name of the second input parameter.
         */
        BooleanOppositionConstraint(final String name, final String otherName) {
            super(name);
            this.otherInputParameter = otherName;
            inputParameterList = new ArrayList<InputParameter>();
            inputParameterList.add(InputParameter.get(otherName));
        }

        @Override
        public List<InputParameter> getInputParameters() {
            return inputParameterList;
        }

        @Override
        protected boolean isFalse() {
            return false;
        }

        @Override
        public String toJavaString() {
            return "assert " + getName() + " == !" + otherInputParameter + ";";
        }

        @Override
        public String toString() {
            return getName() + " = ¬" + otherInputParameter;
        }
    }

    /**
     * The name of the constrained input parameter, also used as the name of the
     * constraint.
     */
    private final String name;
    /**
     * The number of the constrained input parameter, also used as the number of the
     * constraint.
     */
    private final int number;

    /**
     * Creates a Boolean constraint based on the left hand side and the right hand
     * side of the expression.
     *
     * @param leftHandSide  The left hand side of the constraint.
     * @param rightHandSide The right hand side of the constraint.
     * @return A Boolean constraint.
     */
    public static BooleanConstraint parse(final String leftHandSide, final String rightHandSide) {
        if ("True".equals(rightHandSide)) {
            return new BooleanTrueConstraint(leftHandSide);
        } else if ("False".equals(rightHandSide)) {
            return new BooleanFalseConstraint(leftHandSide);
        } else if (rightHandSide.startsWith("¬")) {
            return new BooleanOppositionConstraint(leftHandSide, rightHandSide.substring(1));
        } else {
            return new BooleanEqualityConstraint(leftHandSide, rightHandSide);
        }
    }

    /**
     * Constructor using the name of the constrained input parameter as its
     * parameter.
     *
     * @param name The name of the constrained input parameter.
     */
    public BooleanConstraint(final String name) {
        this.name = name;
        this.number = Integer.parseInt(name.substring(1));
    }

    @Override
    public abstract List<InputParameter> getInputParameters();

    @Override
    public List<InternalVariable> getInternalVariables() {
        return Collections.emptyList();
    }

    /**
     * Returns the name of the constraint, which is equal to the name of the
     * constrained input parameter.
     *
     * @return The name of the constraint.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the number of the constraint, which is equal to the number of the
     * constrained input parameter. This number is useful for sorting the
     * constraints numerically.
     *
     * @return The number of the constraint.
     */
    public int getNumber() {
        return number;
    }

    @Override
    protected boolean resolve(final BooleanFunction booleanFunction) {
        return false;
    }
}
