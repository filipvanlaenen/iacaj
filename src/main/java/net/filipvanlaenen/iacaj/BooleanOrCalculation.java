package net.filipvanlaenen.iacaj;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing an Or calculation.
 */
public final class BooleanOrCalculation extends BooleanCalculation {
    /**
     * Constructor taking the right hand side string as parameter.
     *
     * @param rightHandSideString A right hand side string representing the Xor
     *                            calculation.
     */
    public BooleanOrCalculation(final String rightHandSideString) {
        super(rightHandSideString);
    }

    /**
     * Constructor taking a list of operands as the parameter.
     *
     * @param operands The list of operands.
     */
    public BooleanOrCalculation(final List<BooleanOperand> operands) {
        super(operands);
    }

    @Override
    protected BooleanOrCalculation deepClone() {
        return new BooleanOrCalculation(getOperands());
    }

    @Override
    protected BooleanOperator getOperator() {
        return BooleanOperator.Or;
    }

    @Override
    protected BooleanRightHandSide resolve(final BooleanFunction booleanFunction) {
        List<BooleanOperand> falseOperands = new ArrayList<BooleanOperand>();
        for (BooleanOperand operand : getOperands()) {
            if (!operand.isNegated()) {
                BooleanExpression be = booleanFunction.getExpression(operand.getName());
                if (be != null) {
                    if (be.isFalse()) {
                        falseOperands.add(operand);
                    } else if (be.isTrue()) {
                        return BooleanConstant.TRUE;
                    }
                }
            }
        }
        removeOperands(falseOperands);
        if (getNumberOfOperands() == 0) {
            return BooleanConstant.FALSE;
        }
        if (getNumberOfOperands() == 1) {
            BooleanOperand firstOperand = getOperands().get(0);
            return new BooleanEquation(firstOperand.getName(), firstOperand.isNegated());
        }
        return this;
    }
}
