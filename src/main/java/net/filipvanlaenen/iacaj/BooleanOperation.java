package net.filipvanlaenen.iacaj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BooleanOperation extends BooleanExpression {
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
        String getSymbol() {
            return symbol;
        }
    }

    /**
     * The input parameters used in the Boolean expression.
     */
    private Set<InputParameter> inputParameters = new HashSet<InputParameter>();
    /**
     * The internal variables used in the Boolean expression.
     */
    private Set<InternalVariable> internalVariables = new HashSet<InternalVariable>();
    /**
     * The variables negated in the Boolean expression.
     */
    private Set<Variable> negatedVariables = new HashSet<Variable>();
    /**
     * The operator in the Boolean expression.
     */
    private Operator operator;
    /**
     * The name of the operation.
     */
    private final String name;

    /**
     * Constructor, creating a Boolean expression based on the left hand side and
     * the right hand side of the expression.
     *
     * @param leftHandSide  The left hand side of the operation.
     * @param rightHandSide The right hand side of the operation.
     */
    public BooleanOperation(final String leftHandSide, final String rightHandSide) {
        this.name = leftHandSide;
        for (Operator o : Operator.values()) {
            if (rightHandSide.contains(o.getSymbol())) {
                operator = o;
            }
        }
        String[] operands = Arrays.stream(rightHandSide.split(operator.getSymbol())).map(String::trim)
                .toArray(String[]::new);
        for (String operand : operands) {
            String operandName = operand;
            boolean negated = operand.startsWith("¬");
            if (negated) {
                operandName = operand.substring(1);
            }
            Variable variable = null;
            if (InputParameter.isInputParameter(operandName)) {
                variable = InputParameter.get(operandName);
                inputParameters.add((InputParameter) variable);
            } else {
                variable = InternalVariable.get(operandName);
                internalVariables.add((InternalVariable) variable);
            }
            if (negated) {
                negatedVariables.add(variable);
            }
        }
    }

    @Override
    public Set<InputParameter> getInputParameters() {
        return inputParameters;
    }

    private Set<InternalVariable> getInternalVariables() {
        return internalVariables;
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
        return operator;
    }

    @Override
    public String toString() {
        List<Variable> variables = new ArrayList<Variable>(getInputParameters());
        variables.addAll(getInternalVariables());
        variables.sort(new Comparator<Variable>() {
            @Override
            public int compare(Variable arg0, Variable arg1) {
                if (arg0 instanceof InputParameter) {
                    if (arg1 instanceof InputParameter) {
                        return arg0.getName().compareTo(arg1.getName());
                    } else {
                        return -1;
                    }
                } else if (arg1 instanceof InputParameter) {
                    return 1;
                } else {
                    return arg0.getName().compareTo(arg1.getName());
                }
            }
        });
        StringBuffer sb = new StringBuffer();
        for (Variable variable : variables) {
            if (sb.length() > 0) {
                sb.append(" " + operator.getSymbol() + " ");
            }
            if (negatedVariables.contains(variable)) {
                sb.append("¬");
            }
            sb.append(variable.getName());
        }
        return name + " = " + sb.toString();
    }

    public boolean isOutputParameter() {
        return OutputParameter.isOutputParameter(name);
    }
}
