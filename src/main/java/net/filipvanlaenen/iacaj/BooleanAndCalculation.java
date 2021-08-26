package net.filipvanlaenen.iacaj;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing an And calculation.
 */
public final class BooleanAndCalculation extends BooleanCalculation {
    /**
     * Constructor taking the right hand side string as parameter.
     *
     * @param rightHandSideString A right hand side string representing the Xor
     *                            calculation.
     */
    public BooleanAndCalculation(final String rightHandSideString) {
        super(rightHandSideString);
    }

    /**
     * Constructor taking a list of operands as the parameter.
     *
     * @param operands The list of operands.
     */
    public BooleanAndCalculation(final List<BooleanOperand> operands) {
        super(operands);
    }

    @Override
    protected BooleanAndCalculation deepClone() {
        return new BooleanAndCalculation(getOperands());
    }

    @Override
    protected BooleanOperator getOperator() {
        return BooleanOperator.And;
    }

    @Override
    protected BooleanRightHandSide resolve(final BooleanFunction booleanFunction) {
        expandOperands(booleanFunction);
        List<BooleanOperand> trueOperands = new ArrayList<BooleanOperand>();
        for (BooleanOperand operand : getOperands()) {
            BooleanExpression be = booleanFunction.getExpression(operand.getName());
            if (be != null) {
                if (be.isTrue() && !operand.isNegated() || be.isFalse() && operand.isNegated()) {
                    trueOperands.add(operand);
                } else if (be.isFalse() && !operand.isNegated() || be.isTrue() && operand.isNegated()) {
                    return BooleanConstant.FALSE;
                }
            }
        }
        removeOperands(trueOperands);
        if (getNumberOfOperands() == 0) {
            return BooleanConstant.TRUE;
        }
        if (getNumberOfOperands() == 1) {
            BooleanOperand firstOperand = getOperands().get(0);
            return new BooleanEquation(firstOperand.getName(), firstOperand.isNegated()).resolve(booleanFunction);
        }
        return this;
    }
}
