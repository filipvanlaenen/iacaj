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
 * Class representing a Boolean operation.
 */
public final class BooleanOperation extends BooleanExpression {
    /**
     * Class representing the right hand side of a Boolean operation.
     */
    public abstract static class BooleanRightHandSide {
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
            protected Operator getOperator() {
                return null;
            }

            @Override
            protected boolean isFalse() {
                return false;
            }

            @Override
            protected BooleanRightHandSide resolve(final BooleanFunction booleanFunction) {
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
         * Class representing a right hand side with a Boolean calculation.
         */
        public static final class BooleanCalculation extends BooleanRightHandSide {
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

                public boolean isNegated() {
                    return negated;
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
             * The operator for the calculation.
             */
            private final Operator operator;
            /**
             * A list with the operands. Note that the same operand can appear more than
             * once in a calculation, therefore this is not a Set.
             */
            private List<BooleanOperand> operands = new ArrayList<BooleanOperand>();

            /**
             * Constructor taking the operator and the right hand side as its parameters.
             *
             * @param operator      The operator for the Boolean calculation.
             * @param rightHandSide The right hand side.
             */
            public BooleanCalculation(final Operator operator, final String rightHandSide) {
                this.operator = operator;
                String[] operandStrings = Arrays.stream(rightHandSide.split(operator.getSymbol())).map(String::trim)
                        .toArray(String[]::new);
                for (String operandString : operandStrings) {
                    operands.add(new BooleanOperand(operandString));

                }
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
            public List<InputParameter> getInputParameters() {
                return operands.stream().filter(new Predicate<BooleanOperand>() {

                    @Override
                    public boolean test(final BooleanOperand operand) {
                        return InputParameter.isInputParameter(operand.getName());
                    }
                }).map(BooleanOperand::getName).map(InputParameter::get).collect(Collectors.toList());
            }

            @Override
            public List<InternalVariable> getInternalVariables() {
                return operands.stream().filter(new Predicate<BooleanOperand>() {
                    @Override
                    public boolean test(final BooleanOperand operand) {
                        return !InputParameter.isInputParameter(operand.getName());
                    }
                }).map(BooleanOperand::getName).map(InternalVariable::get).collect(Collectors.toList());
            }

            @Override
            protected Operator getOperator() {
                return operator;
            }

            @Override
            protected boolean isFalse() {
                return false;
            }

            @Override
            protected BooleanRightHandSide resolve(final BooleanFunction booleanFunction) {
                List<BooleanOperand> toBeRemoved = new ArrayList<BooleanOperand>();
                for (BooleanOperand operand : operands) {
                    if (operator.equals(Operator.Xor) && !operand.isNegated()) {
                        BooleanExpression be = booleanFunction.get(operand.getName());
                        if (be != null && be.isFalse()) {
                            toBeRemoved.add(operand);
                        }
                    }
                }
                operands.removeAll(toBeRemoved);
                if (operands.isEmpty()) {
                    if (operator.equals(Operator.Xor)) {
                        return new BooleanConstant(false);
                    }
                }
                return this;
            }

            @Override
            public String toJavaString() {
                return exportToString(operator.getJavaSymbol(), BooleanOperand::toJavaString);
            }

            @Override
            public String toString() {
                return exportToString(operator.getSymbol(), BooleanOperand::toString);
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
            public List<InputParameter> getInputParameters() {
                return Collections.emptyList();
            }

            @Override
            public List<InternalVariable> getInternalVariables() {
                return Collections.emptyList();
            }

            @Override
            protected Operator getOperator() {
                return null;
            }

            @Override
            protected boolean isFalse() {
                return !value;
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
        private static final BooleanRightHandSide TRUE = new BooleanConstant(true);
        /**
         * The Boolean constant for False.
         */
        private static final BooleanRightHandSide FALSE = new BooleanConstant(false);

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
                for (Operator o : Operator.values()) {
                    if (rightHandSide.contains(o.getSymbol())) {
                        return new BooleanCalculation(o, rightHandSide);
                    }
                }
                return new BooleanEquation(rightHandSide);
            }
        }

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
        protected abstract Operator getOperator();

        protected abstract boolean isFalse();

        protected abstract BooleanRightHandSide resolve(final BooleanFunction booleanFunction);

        /**
         * Exports the right hand side to a Java code string.
         *
         * @return A Java code string representing the right hand side.
         */
        protected abstract String toJavaString();
    }

    /**
     * Enumeration representing the operators.
     */
    public enum Operator {
        /**
         * The logical AND operator.
         */
        And("∧", "&"),
        /**
         * The logical OR operator.
         */
        Or("∨", "|"),
        /**
         * The logical XOR operator.
         */
        Xor("⊻", "^");

        /**
         * The symbol.
         */
        private final String symbol;
        /**
         * The symbol for Java.
         */
        private final String javaSymbol;

        /**
         * Constructor using the symbol as the parameter.
         *
         * @param symbol     The symbol for the operator.
         * @param javaSymbol The symbol for the operator in Java.
         */
        Operator(final String symbol, final String javaSymbol) {
            this.symbol = symbol;
            this.javaSymbol = javaSymbol;
        }

        /**
         * Returns the symbol of the operartor for Java.
         *
         * @return The symbol of the operator for Java.
         */
        public String getJavaSymbol() {
            return javaSymbol;
        }

        /**
         * Returns the symbol of the operartor.
         *
         * @return The symbol of the operator.
         */
        public String getSymbol() {
            return symbol;
        }
    }

    /**
     * The name of the operation.
     */
    private final String name;
    /**
     * The number of the operation.
     */
    private final int number;
    /**
     * The right hand side of the operation.
     */
    private BooleanRightHandSide rightHandSide;

    /**
     * Constructor, creating a Boolean expression based on the left hand side and
     * the right hand side of the expression.
     *
     * @param leftHandSide  The left hand side of the operation.
     * @param rightHandSide The right hand side of the operation.
     */
    public BooleanOperation(final String leftHandSide, final String rightHandSide) {
        this.name = leftHandSide;
        this.number = Integer.parseInt(leftHandSide.substring(1));
        this.rightHandSide = BooleanRightHandSide.parse(rightHandSide);
    }

    @Override
    public List<InputParameter> getInputParameters() {
        return rightHandSide.getInputParameters();
    }

    @Override
    public List<InternalVariable> getInternalVariables() {
        return rightHandSide.getInternalVariables();
    }

    /**
     * Returns the name of the operation.
     *
     * @return The name of the operation.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the number of the operation.
     *
     * @return The number of the operation.s
     */
    public int getNumber() {
        return number;
    }

    /**
     * Returns the operator of the Boolean expression.
     *
     * @return The operator of the Boolean expression.
     */
    public Operator getOperator() {
        return rightHandSide.getOperator();
    }

    @Override
    protected boolean isFalse() {
        return rightHandSide.isFalse();
    }

    /**
     * Returns whether the Boolean operation calculates an output parameter or not.
     *
     * @return True if the Boolean operation calculates an output parameter.
     */
    public boolean isOutputParameter() {
        return OutputParameter.isOutputParameter(name);
    }

    @Override
    protected boolean resolve(final BooleanFunction booleanFunction) {
        String before = rightHandSide.toString();
        rightHandSide = rightHandSide.resolve(booleanFunction);
        String after = rightHandSide.toString();
        return !before.equals(after);
    }

    @Override
    public String toJavaString() {
        return "boolean " + name + " = " + rightHandSide.toJavaString() + ";";
    }

    @Override
    public String toString() {
        return name + " = " + rightHandSide.toString();
    }
}
