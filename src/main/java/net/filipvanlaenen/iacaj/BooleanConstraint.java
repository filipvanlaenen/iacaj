package net.filipvanlaenen.iacaj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class representing a Boolean constraint.
 */
public abstract class BooleanConstraint extends BooleanExpression {
    /**
     * Class representing a Boolean constraint requiring an input parameter to be true.
     */
    static class BooleanTrueConstraint extends BooleanConstraint {
        /**
         * Constructor using the name of the input parameter as its parameter.
         *
         * @param name The name of the input parameter.
         */
        BooleanTrueConstraint(final String name) {
            super(name);
        }

        @Override
        protected BooleanExpression deepClone() {
            return new BooleanTrueConstraint(getName());
        }

        @Override
        public List<InputParameter> getInputParameters() {
            return Collections.emptyList();
        }

        @Override
        public boolean equals(final Object object) {
            if (object instanceof BooleanTrueConstraint) {
                BooleanTrueConstraint other = (BooleanTrueConstraint) object;
                return other.getName().equals(getName());
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return getName().hashCode();
        }

        @Override
        protected boolean isFalse() {
            return false;
        }

        @Override
        protected boolean isTrue() {
            return true;
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
     * Class representing a Boolean constraint requiring an input parameter to be false.
     */
    static class BooleanFalseConstraint extends BooleanConstraint {
        /**
         * Constructor using the name of the input parameter as its parameter.
         *
         * @param name The name of the input parameter.
         */
        BooleanFalseConstraint(final String name) {
            super(name);
        }

        @Override
        protected BooleanExpression deepClone() {
            return new BooleanFalseConstraint(getName());
        }

        @Override
        public boolean equals(final Object object) {
            if (object instanceof BooleanFalseConstraint) {
                BooleanFalseConstraint other = (BooleanFalseConstraint) object;
                return other.getName().equals(getName());
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return -getName().hashCode();
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
        protected boolean isTrue() {
            return false;
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
     * Class representing a Boolean constraint requiring that two input parameters are equal to each other.
     */
    static class BooleanEqualityConstraint extends BooleanConstraint {
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
        protected BooleanExpression deepClone() {
            return new BooleanEqualityConstraint(getName(), otherInputParameter);
        }

        @Override
        public boolean equals(final Object object) {
            if (object instanceof BooleanEqualityConstraint) {
                BooleanEqualityConstraint other = (BooleanEqualityConstraint) object;
                return other.getName().equals(getName()) && other.otherInputParameter.equals(otherInputParameter);
            } else {
                return false;
            }
        }

        @Override
        public List<InputParameter> getInputParameters() {
            return inputParameterList;
        }

        @Override
        public int hashCode() {
            // EQMU: Replacing addition with subtraction produces an equivalent mutant.
            return getName().hashCode() + otherInputParameter.hashCode();
        }

        @Override
        protected boolean isFalse() {
            return false;
        }

        @Override
        protected boolean isTrue() {
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
     * Class representing a Boolean constraint requiring that two input parameters are opposite to each other.
     */
    static class BooleanOppositionConstraint extends BooleanConstraint {
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
        protected BooleanExpression deepClone() {
            return new BooleanOppositionConstraint(getName(), otherInputParameter);
        }

        @Override
        public boolean equals(final Object object) {
            if (object instanceof BooleanOppositionConstraint) {
                BooleanOppositionConstraint other = (BooleanOppositionConstraint) object;
                return other.getName().equals(getName()) && other.otherInputParameter.equals(otherInputParameter);
            } else {
                return false;
            }
        }

        @Override
        public List<InputParameter> getInputParameters() {
            return inputParameterList;
        }

        @Override
        public int hashCode() {
            // EQMU: Replacing addition with subtraction produces an equivalent mutant.
            return getName().hashCode() + otherInputParameter.hashCode();
        }

        @Override
        protected boolean isFalse() {
            return false;
        }

        @Override
        protected boolean isTrue() {
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
     * The name of the constrained input parameter, also used as the name of the constraint.
     */
    private final String name;
    /**
     * The number of the constrained input parameter, also used as the number of the constraint.
     */
    private final int number;

    /**
     * Creates a Boolean constraint based on the left hand side and the right hand side of the expression.
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
     * Constructor using the name of the constrained input parameter as its parameter.
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
    public final List<InternalVariable> getInternalVariables() {
        return Collections.emptyList();
    }

    /**
     * Returns the name of the constraint, which is equal to the name of the constrained input parameter.
     *
     * @return The name of the constraint.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the number of the constraint, which is equal to the number of the constrained input parameter. This
     * number is useful for sorting the constraints numerically.
     *
     * @return The number of the constraint.
     */
    public int getNumber() {
        return number;
    }

    @Override
    protected void resolve(final BooleanFunction booleanFunction) {
    }
}
