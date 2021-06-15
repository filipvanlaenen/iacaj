package net.filipvanlaenen.iacaj;

import java.util.List;

/**
 * Class representing a Boolean operation.
 */
public final class BooleanOperation extends BooleanExpression {
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
     * the right hand side of the expression as strings.
     *
     * @param leftHandSide        The left hand side of the operation.
     * @param rightHandSideString The right hand side of the operation as a string.
     */
    public BooleanOperation(final String leftHandSide, final String rightHandSideString) {
        this(leftHandSide, BooleanRightHandSide.parse(rightHandSideString));
    }

    /**
     * Constructor, creating a Boolean expression based on the left hand side and
     * the right hand side of the expression.
     *
     * @param leftHandSide  The left hand side of the operation.
     * @param rightHandSide The right hand side of the operation.
     */
    public BooleanOperation(final String leftHandSide, final BooleanRightHandSide rightHandSide) {
        this.name = leftHandSide;
        this.number = Integer.parseInt(leftHandSide.substring(1));
        this.rightHandSide = rightHandSide;
    }

    @Override
    protected BooleanOperation deepClone() {
        return new BooleanOperation(name, rightHandSide.deepClone());
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
    public BooleanOperator getOperator() {
        return rightHandSide.getOperator();
    }

    /**
     * Returns the right hand side of the Boolean expression.
     *
     * @return The right hand side of the Boolean expression.
     */
    BooleanRightHandSide getRightHandSide() {
        return rightHandSide;
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
    protected boolean isTrue() {
        return rightHandSide.isTrue();
    }

    @Override
    protected void resolve(final BooleanFunction booleanFunction) {
        rightHandSide = rightHandSide.resolve(booleanFunction);
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
