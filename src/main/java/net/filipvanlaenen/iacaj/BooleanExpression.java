package net.filipvanlaenen.iacaj;

import java.util.List;
import java.util.Set;

/**
 * Class representing a Boolean expression. A Boolean expression consists of a
 * result name, an equation sign, and operands and operators.
 */
public abstract class BooleanExpression {

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
        String leftHandSide = elements[0].trim();
        String rightHandSide = elements[1].trim();
        if (InputParameter.isInputParameter(leftHandSide)) {
            return BooleanConstraint.parse(leftHandSide, rightHandSide);
        } else {
            return new BooleanOperation(leftHandSide, rightHandSide);
        }
    }

    /**
     * Returns the input parameters of the Boolean expression.
     *
     * @return The input parameters of the Boolean expression.
     */
    public abstract List<InputParameter> getInputParameters();

}
