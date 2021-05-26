package net.filipvanlaenen.iacaj;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class representing the right hand side of a Boolean operation.
 */
public abstract class BooleanRightHandSide {
    /**
     * Class representing a right hand side of a Boolean operation with an equation
     * (or negation).
     */
    public static final class BooleanEquation extends BooleanRightHandSide {
        /**
         * Whether or not the equation is negated.
         */
        private final boolean negated;
        /**
         * The name of the operand.
         */
        private final String operand;

        /**
         * Constructor taking the right hand side as the parameter.
         *
         * @param rightHandSide The right hand side to be parsed.
         */
        public BooleanEquation(final String rightHandSide) {
            negated = rightHandSide.startsWith("¬");
            if (negated) {
                operand = rightHandSide.substring(1);
            } else {
                operand = rightHandSide;
            }
        }

        /**
         * Constructor taking the operand and whether it should be negated as
         * parameters.
         *
         * @param operand The name of the operand.
         * @param negated Whether the operand should be negated or not.
         */
        public BooleanEquation(final String operand, final boolean negated) {
            this.operand = operand;
            this.negated = negated;
        }

        @Override
        protected BooleanRightHandSide.BooleanEquation deepClone() {
            return new BooleanEquation(operand, negated);
        }

        @Override
        public List<InputParameter> getInputParameters() {
            if (InputParameter.isInputParameter(operand)) {
                return Arrays.asList(InputParameter.get(operand));
            } else {
                return Collections.emptyList();
            }
        }

        @Override
        public List<InternalVariable> getInternalVariables() {
            if (!InputParameter.isInputParameter(operand)) {
                return Arrays.asList(InternalVariable.get(operand));
            } else {
                return Collections.emptyList();
            }
        }

        @Override
        protected BooleanOperator getOperator() {
            return null;
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
        protected BooleanRightHandSide resolve(final BooleanFunction booleanFunction) {
            BooleanExpression be = booleanFunction.getExpression(operand);
            if (be != null) {
                if (negated) {
                    if (be.isTrue()) {
                        return FALSE;
                    } else if (be.isFalse()) {
                        return TRUE;
                    }
                } else {
                    if (be.isTrue()) {
                        return TRUE;
                    } else if (be.isFalse()) {
                        return FALSE;
                    }
                }
            }
            return this;
        }

        @Override
        public String toJavaString() {
            return (negated ? "!" : "") + operand;
        }

        @Override
        public String toString() {
            return (negated ? "¬" : "") + operand;
        }
    }

    /**
     * Class representing a right hand side of a Boolean operation with a constant,
     * i.e. either True or False.
     */
    public static final class BooleanConstant extends BooleanRightHandSide {
        /**
         * The constant value.
         */
        private final boolean value;

        /**
         * Constructor taking the constant value as its parameter.
         *
         * @param value The constant value.
         */
        public BooleanConstant(final boolean value) {
            this.value = value;
        }

        @Override
        protected BooleanRightHandSide.BooleanConstant deepClone() {
            return new BooleanConstant(value);
        }

        @Override
        public List<InputParameter> getInputParameters() {
            return Collections.emptyList();
        }

        @Override
        public List<InternalVariable> getInternalVariables() {
            return Collections.emptyList();
        }

        @Override
        protected BooleanOperator getOperator() {
            return null;
        }

        @Override
        protected boolean isFalse() {
            return !value;
        }

        @Override
        protected boolean isTrue() {
            return value;
        }

        @Override
        protected BooleanRightHandSide resolve(final BooleanFunction booleanFunction) {
            return this;
        }

        @Override
        public String toJavaString() {
            return value ? "true" : "false";
        }

        @Override
        public String toString() {
            return value ? "True" : "False";
        }
    }

    /**
     * The Boolean constant for True.
     */
    static final BooleanRightHandSide TRUE = new BooleanConstant(true);
    /**
     * The Boolean constant for False.
     */
    static final BooleanRightHandSide FALSE = new BooleanConstant(false);

    /**
     * Constructor taking the right hand side string as its parameter.
     *
     * @param rightHandSide String representing the right hand side.
     * @return An object representing the right hand side string.
     */
    public static BooleanRightHandSide parse(final String rightHandSide) {
        if (rightHandSide.equals("True")) {
            return TRUE;
        } else if (rightHandSide.equals("False")) {
            return FALSE;
        } else {
            for (BooleanOperator o : BooleanOperator.values()) {
                if (rightHandSide.contains(o.getSymbol())) {
                    return o.createBooleanCalculation(rightHandSide);
                }
            }
            return new BooleanEquation(rightHandSide);
        }
    }

    /**
     * Returns a deep clone of the Boolean right hand side.
     *
     * @return A deep clone of the Boolean right hand side.
     */
    protected abstract BooleanRightHandSide deepClone();

    /**
     * Returns a list with the input parameters used in the right hand side.
     *
     * @return A list with the input parameters used in the right hand side.
     */
    public abstract List<InputParameter> getInputParameters();

    /**
     * Returns a list with the internal variables used in the right hand side.
     *
     * @return A list with the internal variables used in the right hand side.
     */
    public abstract List<InternalVariable> getInternalVariables();

    /**
     * Returns the operator used in the right hand side.
     *
     * @return The operator used in the right hand side.
     */
    protected abstract BooleanOperator getOperator();

    /**
     * Returns whether the right hand side evaluates to False.
     *
     * @return True if the right hand side evaluates to False.
     */
    protected abstract boolean isFalse();

    /**
     * Returns whether the right hand side evaluates to True.
     *
     * @return True if the right hand side evaluates to True.
     */
    protected abstract boolean isTrue();

    /**
     * Resolves the right hand side.
     *
     * @param booleanFunction The Boolean function giving the context for the right
     *                        hand side.
     * @return The result of resolving the right hand side.
     */
    protected abstract BooleanRightHandSide resolve(BooleanFunction booleanFunction);

    /**
     * Exports the right hand side to a Java code string.
     *
     * @return A Java code string representing the right hand side.
     */
    protected abstract String toJavaString();
}
