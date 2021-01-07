package net.filipvanlaenen.iacaj;

import java.util.List;

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

    /**
     * Returns the internal variables of the Boolean expression.
     *
     * @return The internal variables of the Boolean expression.
     */
    protected abstract List<InternalVariable> getInternalVariables();

    /**
     * Returns the name of the Boolean expression.
     *
     * @return The name of the Boolean expression.
     */
    protected abstract String getName();

    /**
     * Returns whether the Boolean expression evaluates to False.
     *
     * @return True if the Boolean expression evaluates to False.
     */
    protected abstract boolean isFalse();

    /**
     * Resolves the Boolean expressions.
     *
     * @param booleanFunction The Boolean function giving the context for the
     *                        expression.
     */
    protected abstract void resolve(BooleanFunction booleanFunction);

    /**
     * Returns a representation of the Boolean expression as Java code.
     *
     * @return Java code representing the Boolean expression.
     */
    public abstract String toJavaString();
}
