package net.filipvanlaenen.iacaj;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Class representing a Boolean expression. A Boolean expression consists of a
 * result name, an equation sign, and operands and operators.
 */
public class BooleanExpression {
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
     * The operator in the Boolean expression.
     */
    private Operator operator;

    /**
     * Constructor, creating a Boolean expression based on the name for the result
     * and a list of operand names.
     *
     * @param resultName   The name for the result.
     * @param operator     The operator.
     * @param operandNames The names of the operands.
     */
    public BooleanExpression(final String resultName, final Operator operator, final String[] operandNames) {
        this.operator = operator;
        for (String operandName : operandNames) {
            // TODO: Differentiate between input parameters and internal variables
            inputParameters.add(InputParameter.get(operandName));
        }
    }

    /**
     * Parses a line into a Boolean expression.
     *
     * @param line A line representing a Boolean expression.
     * @return A Boolean expression.
     */
    public static BooleanExpression parse(final String line) {
        if (line.isEmpty() || line.startsWith("#")) {
            return null;
        }
        String[] elements = line.split("=");
        String resultName = elements[0].trim();
        String operation = elements[1].trim();
        Operator operator = null;
        for (Operator o : Operator.values()) {
            if (operation.contains(o.getSymbol())) {
                operator = o;
            }
        }
        // TODO: Negations
        String[] operandNames = Arrays.stream(operation.split(operator.getSymbol())).map(String::trim)
                .toArray(String[]::new);
        return new BooleanExpression(resultName, operator, operandNames);
    }

    /**
     * Returns the input parameters of the Boolean expression.
     *
     * @return The input parameters of the Boolean expression.
     */
    public Set<InputParameter> getInputParameters() {
        return inputParameters;
    }

    /**
     * Returns the operator of the Boolean expression.
     *
     * @return The operator of the Boolean expression.
     */
    public Operator getOperator() {
        return operator;
    }
}
