package net.filipvanlaenen.iacaj;

import net.filipvanlaenen.kolektoj.Collection;
import net.filipvanlaenen.kolektoj.ModifiableCollection;
import net.filipvanlaenen.kolektoj.array.ModifiableArrayCollection;

/**
 * Class representing an And calculation.
 */
@Deprecated
public final class BooleanAndCalculation extends BooleanCalculation {
    /**
     * Constructor taking the right hand side string as parameter.
     *
     * @param rightHandSideString A right hand side string representing the Xor calculation.
     */
    public BooleanAndCalculation(final String rightHandSideString) {
        super(rightHandSideString);
    }

    /**
     * Constructor taking a list of operands as the parameter.
     *
     * @param operands The list of operands.
     */
    public BooleanAndCalculation(final Collection<BooleanOperand> operands) {
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
        boolean eliminated = true;
        while (eliminated) {
            BooleanOperand operandA = null;
            BooleanOperand operandB = null;
            for (BooleanOperand a : getOperands()) {
                for (BooleanOperand b : getOperands()) {
                    if (a != b && a.getName().equals(b.getName())) {
                        operandA = a;
                        operandB = b;
                    }
                }
            }
            eliminated = operandA != null;
            if (eliminated) {
                if (operandA.isNegated() == operandB.isNegated()) {
                    if (getNumberOfOperands() == 2) {
                        return new BooleanEquation(operandA.getName(), operandA.isNegated()).resolve(booleanFunction);
                    } else {
                        removeOperand(operandA);
                    }
                } else {
                    return BooleanConstant.FALSE;
                }
            }
        }
        ModifiableCollection<BooleanOperand> trueOperands = new ModifiableArrayCollection<BooleanOperand>();
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
            BooleanOperand firstOperand = getOperands().get();
            return new BooleanEquation(firstOperand.getName(), firstOperand.isNegated()).resolve(booleanFunction);
        }
        return this;
    }
}
