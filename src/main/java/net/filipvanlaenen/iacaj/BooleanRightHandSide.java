package net.filipvanlaenen.iacaj;

import net.filipvanlaenen.kolektoj.OrderedCollection;

/**
 * Class representing the right hand side of a Boolean operation.
 */
public abstract class BooleanRightHandSide {
    /**
     * Constructor taking the right hand side string as its parameter.
     *
     * @param rightHandSide String representing the right hand side.
     * @return An object representing the right hand side string.
     */
    public static BooleanRightHandSide parse(final String rightHandSide) {
        if (rightHandSide.equals("True")) {
            return BooleanConstant.TRUE;
        } else if (rightHandSide.equals("False")) {
            return BooleanConstant.FALSE;
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
    public abstract OrderedCollection<InputParameter> getInputParameters();

    /**
     * Returns a list with the internal variables used in the right hand side.
     *
     * @return A list with the internal variables used in the right hand side.
     */
    public abstract OrderedCollection<InternalVariable> getInternalVariables();

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
