package net.filipvanlaenen.iacaj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BooleanOperation extends BooleanExpression {
    public abstract static class BooleanRightHandSide {
        public static class BooleanEquation extends BooleanRightHandSide {
            private final boolean negated;
            private final String operand;

            public BooleanEquation(String rightHandSide) {
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
            public String toString() {
                return (negated ? "¬" : "") + operand;
            }
        }

        public static class BooleanCalculation extends BooleanRightHandSide {
            public class BooleanOperand {
                private final boolean negated;
                private final String name;
                private final int number;

                BooleanOperand(String operandString) {
                    negated = operandString.startsWith("¬");
                    if (negated) {
                        name = operandString.substring(1);
                    } else {
                        name = operandString;
                    }
                    number = Integer.parseInt(name.substring(1));
                }

                public String getName() {
                    return name;
                }

                public boolean isInputParameter() {
                    return InputParameter.isInputParameter(name);
                }

                @Override
                public String toString() {
                    return (negated ? "¬" : "") + name;
                }

                public int getNumber() {
                    return number;
                }
            }

            private final Operator operator;
            private List<BooleanOperand> operands = new ArrayList<BooleanOperand>();

            public BooleanCalculation(Operator operator, String rightHandSide) {
                this.operator = operator;
                String[] operandStrings = Arrays.stream(rightHandSide.split(operator.getSymbol())).map(String::trim)
                        .toArray(String[]::new);
                for (String operandString : operandStrings) {
                    operands.add(new BooleanOperand(operandString));

                }
            }

            @Override
            public List<InputParameter> getInputParameters() {
                return operands.stream().filter(new Predicate<BooleanOperand>() {

                    @Override
                    public boolean test(BooleanOperand operand) {
                        return InputParameter.isInputParameter(operand.getName());
                    }
                }).map(BooleanOperand::getName).map(InputParameter::get).collect(Collectors.toList());
            }

            @Override
            public List<InternalVariable> getInternalVariables() {
                return operands.stream().filter(new Predicate<BooleanOperand>() {

                    @Override
                    public boolean test(BooleanOperand operand) {
                        return !InputParameter.isInputParameter(operand.getName());
                    }
                }).map(BooleanOperand::getName).map(InternalVariable::get).collect(Collectors.toList());
            }

            @Override
            protected Operator getOperator() {
                return operator;
            }

            @Override
            public String toString() {
                operands.sort(new Comparator<BooleanOperand>() {
                    @Override
                    public int compare(BooleanOperand arg0, BooleanOperand arg1) {
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
                return String.join(" " + operator.getSymbol() + " ",
                        operands.stream().map(BooleanOperand::toString).collect(Collectors.toList()));
            }
        }

        public static class BooleanConstant extends BooleanRightHandSide {
            private final boolean value;

            public BooleanConstant(boolean value) {
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
            public String toString() {
                return value ? "True" : "False";
            }
        }

        private static final BooleanRightHandSide TRUE = new BooleanConstant(true);
        private static final BooleanRightHandSide FALSE = new BooleanConstant(false);

        public static BooleanRightHandSide parse(String rightHandSide) {
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

        public abstract List<InputParameter> getInputParameters();

        public abstract List<InternalVariable> getInternalVariables();

        protected abstract Operator getOperator();
    }

    /**
     * Enumeration representing the operators.
     */
    public enum Operator {
        /**
         * The logical AND operator.
         */
        And("∧"),
        /**
         * The logical OR operator.
         */
        Or("∨"),
        /**
         * The logical XOR operator.
         */
        Xor("⊻");

        /**
         * The symbol.
         */
        private final String symbol;

        /**
         * Constructor using the symbol as the parameter.
         *
         * @param symbol The symbol for the operator.
         */
        Operator(final String symbol) {
            this.symbol = symbol;
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
    private final int number;
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

    public String getName() {
        return name;
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
    public String toString() {
        return name + " = " + rightHandSide.toString();
    }

    public boolean isOutputParameter() {
        return OutputParameter.isOutputParameter(name);
    }

    public int getNumber() {
        return number;
    }
}
