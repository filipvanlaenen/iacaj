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
     * The input parameters used in the Boolean expression.
     */
    private Set<InputParameter> inputParameters = new HashSet<InputParameter>();

    /**
     * Constructor, creating a Boolean expression based on the name for the result
     * and a list of operand names.
     *
     * @param resultName   The name for the result.
     * @param operandNames The names of the operands.
     */
    public BooleanExpression(final String resultName, final String[] operandNames) {
        for (String operandName : operandNames) {
            // TODO: Differentiate between input parameters and internal variables
            // TODO: Negations
            // TODO: Look-up based on operand name
            inputParameters.add(new InputParameter());
        }
    }

    /**
     * Parses a line into a Boolean expression.
     *
     * @param line A line representing a Boolean expression.
     * @return A Boolean expression.
     */
    public static BooleanExpression parse(final String line) {
        // TODO: Ignore comments starting with #
        // TODO: Ignore empty lines
        String[] elements = line.split("=");
        String resultName = elements[0].trim();
        String operation = elements[1].trim();
        // TODO: Operator OR
        // TODO: Operator XOR
        String[] operandNames = Arrays.stream(operation.split("∧")).map(String::trim).toArray(String[]::new);
        return new BooleanExpression(resultName, operandNames);
    }

    /**
     * Returns the input parameters of the Boolean expression.
     *
     * @return The input parameters of the Boolean expression.
     */
    public Set<InputParameter> getInputParameters() {
        return inputParameters;
    }
}
