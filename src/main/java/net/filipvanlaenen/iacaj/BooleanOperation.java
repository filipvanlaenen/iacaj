package net.filipvanlaenen.iacaj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
            if (operand.startsWith("¬")) {
                operandName = operand.substring(1);
            }
            if (InputParameter.isInputParameter(operandName)) {
                inputParameters.add(InputParameter.get(operandName));
            } else {
                internalVariables.add(InternalVariable.get(operandName));
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
        List<String> variableNames = variables.stream().map(Variable::getName).collect(Collectors.toList());
        return name + " = " + String.join(" " + operator.getSymbol() + " ", variableNames);
    }
}
