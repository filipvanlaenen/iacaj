package net.filipvanlaenen.iacaj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Class representing a right hand side with a Boolean calculation.
 */
public abstract class BooleanCalculation extends BooleanRightHandSide {
    /**
     * Class representing an operand in a Boolean calculation.
     */
    public final class BooleanOperand {
        /**
         * Whether or not the operand is negated in the calculation.
         */
        private final boolean negated;
        /**
         * The name of the operand.
         */
        private final String name;
        /**
         * The number of the operand, extracted from the name.
         */
        private final int number;

        /**
         * Constructor taking a string representing the operand as its parameter.
         *
         * @param operandString The string representing the operand.
         */
        BooleanOperand(final String operandString) {
            negated = operandString.startsWith("¬");
            if (negated) {
                name = operandString.substring(1);
            } else {
                name = operandString;
            }
            number = Integer.parseInt(name.substring(1));
        }

        /**
         * Constructor taking the name and whether the operand is negated as its
         * parameters.
         *
         * @param name    The name of the operand.
         * @param negated Whether the operand is negated.
         */
        BooleanOperand(final String name, final boolean negated) {
            this.name = name;
            this.negated = negated;
            number = Integer.parseInt(name.substring(1));
        }

        /**
         * Returns the name of the operand.
         *
         * @return The name of the operand.
         */
        public String getName() {
            return name;
        }

        /**
         * Returns the number of the operand, useful for sorting.
         *
         * @return The number of the operand.
         */
        public int getNumber() {
            return number;
        }

        /**
         * Returns whether the operand is an input parameter or not.
         *
         * @return True if the operand is an input parameter, false otherwise.
         */
        public boolean isInputParameter() {
            return InputParameter.isInputParameter(name);
        }

        /**
         * Returns whether the operand is negated.
         *
         * @return True if the operand is negated.
         */
        public boolean isNegated() {
            return negated;
        }

        /**
         * Returns a negated version of the operand.
         *
         * @return A negated version.
         */
        public BooleanOperand negated() {
            return new BooleanOperand(name, !negated);
        }

        /**
         * Returns a representation of the operand as a Java string.
         *
         * @return The operand as a Java string.
         */
        public String toJavaString() {
            return (negated ? "!" : "") + name;
        }

        @Override
        public String toString() {
            return (negated ? "¬" : "") + name;
        }
    }

    /**
     * A list with the operands. Note that the same operand can appear more than
     * once in a calculation, therefore this is not a Set.
     */
    private List<BooleanOperand> operands = new ArrayList<BooleanOperand>();

    /**
     * Constructor taking the operator and the right hand side as its parameters.
     *
     * @param rightHandSide The right hand side.
     */
    public BooleanCalculation(final String rightHandSide) {
        String[] operandStrings = Arrays.stream(rightHandSide.split(getOperator().getSymbol())).map(String::trim)
                .toArray(String[]::new);
        for (String operandString : operandStrings) {
            operands.add(new BooleanOperand(operandString));
        }
    }

    /**
     * Constructor taking a list of operands as the parameter.
     *
     * @param operands The list of operands.
     */
    public BooleanCalculation(final List<BooleanOperand> operands) {
        this.operands = new ArrayList<BooleanOperand>(operands);
    }

    /**
     * Add an operand to the Boolean calculation.
     *
     * @param operandToBeAdded The operand to be added to the Boolean calculation.
     */
    protected void addOperand(final BooleanOperand operandToBeAdded) {
        operands.add(operandToBeAdded);
    }

    /**
     * Exports the Boolean calculation to a string.
     *
     * @param operatorString      The string to be used to represent the operator.
     * @param operandExportMethod The method to be used to export the operands.
     * @return A string representing the Boolean calculation.
     */
    private String exportToString(final String operatorString,
            final Function<BooleanOperand, String> operandExportMethod) {
        operands.sort(new Comparator<BooleanOperand>() {
            @Override
            public int compare(final BooleanOperand arg0, final BooleanOperand arg1) {
                if (arg0.isInputParameter()) {
                    if (arg1.isInputParameter()) {
                        return arg0.getNumber() - arg1.getNumber();
                    } else {
                        return -1;
                    }
                } else if (arg1.isInputParameter()) {
                    return 1;
                } else {
                    return arg0.getNumber() - arg1.getNumber();
                }
            }
        });
        return String.join(" " + operatorString + " ",
                operands.stream().map(operandExportMethod).collect(Collectors.toList()));
    }

    @Override
    public final List<InputParameter> getInputParameters() {
        return operands.stream().filter(new Predicate<BooleanOperand>() {

            @Override
            public boolean test(final BooleanOperand operand) {
                return InputParameter.isInputParameter(operand.getName());
            }
        }).map(BooleanOperand::getName).map(InputParameter::get).collect(Collectors.toList());
    }

    @Override
    public final List<InternalVariable> getInternalVariables() {
        return operands.stream().filter(new Predicate<BooleanOperand>() {
            @Override
            public boolean test(final BooleanOperand operand) {
                return !InputParameter.isInputParameter(operand.getName());
            }
        }).map(BooleanOperand::getName).map(InternalVariable::get).collect(Collectors.toList());
    }

    /**
     * Returns the number of operands in the Boolean calculation.
     *
     * @return The number of operands in the Boolean calculation.
     */
    protected int getNumberOfOperands() {
        return operands.size();
    }

    /**
     * Returns an unmodifiable list of operands.
     *
     * @return An unmodifiable list of operands.
     */
    protected List<BooleanOperand> getOperands() {
        return Collections.unmodifiableList(operands);
    }

    @Override
    protected final boolean isFalse() {
        return false;
    }

    @Override
    protected final boolean isTrue() {
        return false;
    }

    /**
     * Remove an operand from the Boolean calculation.
     *
     * @param operandToBeRemoved The operand to be removed from the Boolean
     *                           calculation.
     */
    protected void removeOperand(final BooleanOperand operandToBeRemoved) {
        operands.remove(operandToBeRemoved);
    }

    /**
     * Removes a list of operands from the Boolean calculation.
     *
     * @param operandsToBeRemoved The operands to be removed from the Boolean
     *                            calculation.
     */
    protected void removeOperands(final List<BooleanOperand> operandsToBeRemoved) {
        operands.removeAll(operandsToBeRemoved);
    }

    @Override
    public final String toJavaString() {
        return exportToString(getOperator().getJavaSymbol(), BooleanOperand::toJavaString);
    }

    @Override
    public final String toString() {
        return exportToString(getOperator().getSymbol(), BooleanOperand::toString);
    }
}
