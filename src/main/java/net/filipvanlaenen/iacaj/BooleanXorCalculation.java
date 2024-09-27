package net.filipvanlaenen.iacaj;

import java.util.Comparator;

import net.filipvanlaenen.kolektoj.Collection;
import net.filipvanlaenen.kolektoj.ModifiableCollection;
import net.filipvanlaenen.kolektoj.SortedCollection;
import net.filipvanlaenen.kolektoj.array.ModifiableArrayCollection;
import net.filipvanlaenen.kolektoj.array.SortedArrayCollection;

/**
 * Class representing an Xor calculation.
 */
public final class BooleanXorCalculation extends BooleanCalculation {
    /**
     * Constructor taking the right hand side string as parameter.
     *
     * @param rightHandSideString A right hand side string representing the Xor calculation.
     */
    public BooleanXorCalculation(final String rightHandSideString) {
        super(rightHandSideString);
    }

    /**
     * Constructor taking a list of operands as the parameter.
     *
     * @param operands The list of operands.
     */
    public BooleanXorCalculation(final Collection<BooleanOperand> operands) {
        super(operands);
    }

    @Override
    protected BooleanCalculation deepClone() {
        return new BooleanXorCalculation(getOperands());
    }

    @Override
    protected BooleanOperator getOperator() {
        return BooleanOperator.Xor;
    }

    @Override
    protected BooleanRightHandSide resolve(final BooleanFunction booleanFunction) {
        expandOperands(booleanFunction);
        ModifiableCollection<BooleanOperand> doubleOperands = new ModifiableArrayCollection<BooleanOperand>();
        SortedCollection<BooleanOperand> sortedOperands =
                new SortedArrayCollection<BooleanOperand>(new Comparator<BooleanOperand>() {
                    @Override
                    public int compare(final BooleanOperand arg0, final BooleanOperand arg1) {
                        return arg0.getName().compareTo(arg1.getName());
                    }
                }, getOperands());
        for (int i = 0; i < sortedOperands.size() - 1; i++) {
            BooleanOperand a = sortedOperands.getAt(i);
            BooleanOperand b = sortedOperands.getAt(i + 1);
            if (a.getName().equals(b.getName())) {
                doubleOperands.add(a);
                doubleOperands.add(b);
                i++;
            }
        }
        if (!doubleOperands.isEmpty()) {
            boolean localResolution = false;
            for (BooleanOperand doubleOperand : doubleOperands) {
                removeOperand(doubleOperand);
                localResolution = localResolution ^ doubleOperand.isNegated();
            }
            if (getNumberOfOperands() == 0) {
                return BooleanConstant.get(localResolution);
            }
            if (!localResolution) {
                BooleanOperand firstOperand = getOperands().get();
                removeOperand(firstOperand);
                addOperand(firstOperand.negated());
            }
        }
        ModifiableCollection<BooleanOperand> falseOperands = new ModifiableArrayCollection<BooleanOperand>();
        ModifiableCollection<BooleanOperand> trueOperands = new ModifiableArrayCollection<BooleanOperand>();
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
            BooleanOperand firstOperand = getOperands().get();
            removeOperand(firstOperand);
            addOperand(firstOperand.negated());
        }
        if (getNumberOfOperands() == 1) {
            BooleanOperand firstOperand = getOperands().get();
            return new BooleanEquation(firstOperand.getName(), firstOperand.isNegated()).resolve(booleanFunction);
        }
        return this;
    }
}
