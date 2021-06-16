package net.filipvanlaenen.iacaj;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class representing a right hand side of a Boolean operation with an equation
 * (or negation).
 */
public final class BooleanEquation extends BooleanRightHandSide {
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

    /**
     * Constructor taking the operand and whether it should be negated as
     * parameters.
     *
     * @param operand The name of the operand.
     * @param negated Whether the operand should be negated or not.
     */
    public BooleanEquation(final String operand, final boolean negated) {
        this.operand = operand;
        this.negated = negated;
    }

    @Override
    protected BooleanEquation deepClone() {
        return new BooleanEquation(operand, negated);
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof BooleanEquation) {
            BooleanEquation other = (BooleanEquation) object;
            return other.operand.equals(operand) && other.negated == negated;
        } else {
            return false;
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
        if (InternalVariable.isInternalVariable(operand)) {
            return Arrays.asList(InternalVariable.get(operand));
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Returns the operand of the Boolean equation.
     *
     * @return The operand of the Boolean equation.
     */
    String getOperand() {
        return operand;
    }

    @Override
    protected BooleanOperator getOperator() {
        return null;
    }

    @Override
    public int hashCode() {
        // Negating the conditional produces an equivalent mutant.
        return negated ? -operand.hashCode() : operand.hashCode();
    }

    @Override
    protected boolean isFalse() {
        return false;
    }

    /**
     * Returns whether the Boolean equation is negated.
     *
     * @return True if the Boolean equation is negated.
     */
    boolean isNegated() {
        return negated;
    }

    @Override
    protected boolean isTrue() {
        return false;
    }

    @Override
    protected BooleanRightHandSide resolve(final BooleanFunction booleanFunction) {
        BooleanExpression be = booleanFunction.getExpression(operand);
        if (be != null) {
            if (be.isTrue()) {
                return BooleanConstant.get(!negated);
            } else if (be.isFalse()) {
                return BooleanConstant.get(negated);
            } else if (be instanceof BooleanOperation) {
                BooleanOperation bo = (BooleanOperation) be;
                BooleanRightHandSide rhs = bo.getRightHandSide();
                if (rhs instanceof BooleanEquation) {
                    BooleanEquation bq = (BooleanEquation) rhs;
                    return new BooleanEquation(bq.getOperand(), negated ^ bq.isNegated());
                }
            }
        }
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
