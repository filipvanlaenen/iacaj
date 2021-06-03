package net.filipvanlaenen.iacaj;

import java.util.ArrayList;
import java.util.List;

import net.filipvanlaenen.iacaj.BooleanConstraint.BooleanEqualityConstraint;
import net.filipvanlaenen.iacaj.BooleanConstraint.BooleanOppositionConstraint;

/**
 * Class representing an Xor calculation.
 */
public final class BooleanXorCalculation extends BooleanCalculation {
    /**
     * Constructor taking the right hand side string as parameter.
     *
     * @param rightHandSideString A right hand side string representing the Xor
     *                            calculation.
     */
    public BooleanXorCalculation(final String rightHandSideString) {
        super(rightHandSideString);
    }

    /**
     * Constructor taking a list of operands as the parameter.
     *
     * @param operands The list of operands.
     */
    public BooleanXorCalculation(final List<BooleanOperand> operands) {
        super(operands);
    }

    @Override
    protected BooleanXorCalculation deepClone() {
        return new BooleanXorCalculation(getOperands());
    }

    @Override
    protected BooleanOperator getOperator() {
        return BooleanOperator.Xor;
    }

    @Override
    protected BooleanRightHandSide resolve(final BooleanFunction booleanFunction) {
        List<BooleanOperand> expandedOperands = new ArrayList<BooleanOperand>();
        List<BooleanOperand> expansions = new ArrayList<BooleanOperand>();
        for (BooleanOperand operand : getOperands()) {
            BooleanExpression be = booleanFunction.getExpression(operand.getName());
            if (be != null) {
                if (be instanceof BooleanEqualityConstraint) {
                    expansions.add(new BooleanOperand(be.getInputParameters().get(0).getName(), operand.isNegated()));
                    expandedOperands.add(operand);
                } else if (be instanceof BooleanOppositionConstraint) {
                    expansions.add(new BooleanOperand(be.getInputParameters().get(0).getName(), !operand.isNegated()));
                    expandedOperands.add(operand);
                }
            }
        }
        removeOperands(expandedOperands);
        addOperands(expansions);
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
                removeOperand(operandA);
                removeOperand(operandB);
                boolean localResolution = operandA.isNegated() ^ operandB.isNegated();
                if (getNumberOfOperands() == 0) {
                    return BooleanConstant.get(localResolution);
                }
                if (!localResolution) {
                    BooleanOperand firstOperand = getOperands().get(0);
                    removeOperand(firstOperand);
                    addOperand(firstOperand.negated());
                }
            }
        }
        List<BooleanOperand> falseOperands = new ArrayList<BooleanOperand>();
        List<BooleanOperand> trueOperands = new ArrayList<BooleanOperand>();
        for (BooleanOperand operand : getOperands()) {
            BooleanExpression be = booleanFunction.getExpression(operand.getName());
            if (be != null) {
                if (be.isTrue() && !operand.isNegated() || be.isFalse() && operand.isNegated()) {
                    trueOperands.add(operand);
                } else if (be.isFalse() && !operand.isNegated() || be.isTrue() && operand.isNegated()) {
                    falseOperands.add(operand);
                }
            }
        }
        removeOperands(falseOperands);
        removeOperands(trueOperands);
        boolean numberOfTrueOperandsIsEven = trueOperands.size() % 2 == 0;
        if (getNumberOfOperands() == 0) {
            return BooleanConstant.get(!numberOfTrueOperandsIsEven);
        } else if (!numberOfTrueOperandsIsEven) {
            BooleanOperand firstOperand = getOperands().get(0);
            removeOperand(firstOperand);
            addOperand(firstOperand.negated());
        }
        if (getNumberOfOperands() == 1) {
            BooleanOperand firstOperand = getOperands().get(0);
            return new BooleanEquation(firstOperand.getName(), firstOperand.isNegated());
        }
        return this;
    }
}
