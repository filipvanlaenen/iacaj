package net.filipvanlaenen.iacaj;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

import net.filipvanlaenen.iacaj.BooleanConstraint.BooleanEqualityConstraint;
import net.filipvanlaenen.iacaj.BooleanConstraint.BooleanOppositionConstraint;
import net.filipvanlaenen.kolektoj.Collection;
import net.filipvanlaenen.kolektoj.ModifiableCollection;
import net.filipvanlaenen.kolektoj.OrderedCollection;
import net.filipvanlaenen.kolektoj.SortedCollection;
import net.filipvanlaenen.kolektoj.array.ArrayCollection;
import net.filipvanlaenen.kolektoj.array.ModifiableArrayCollection;
import net.filipvanlaenen.kolektoj.array.SortedArrayCollection;
import net.filipvanlaenen.kolektoj.collectors.Collectors;

/**
 * Class representing a right hand side with a Boolean calculation.
 */
public abstract class BooleanCalculation extends BooleanRightHandSide {
    /**
     * A list with the operands. Note that the same operand can appear more than once in a calculation.
     */
    private ModifiableCollection<BooleanOperand> operands = new ModifiableArrayCollection<BooleanOperand>();

    static final class OperandComparator implements Comparator<BooleanOperand> {
        @Override
        public int compare(final BooleanOperand arg0, final BooleanOperand arg1) {
            if (arg0.isInputParameter() && !arg1.isInputParameter()) {
                return -1;
            } else if (!arg0.isInputParameter() && arg1.isInputParameter()) {
                return 1;
            }
            int difference = arg0.getNumber() - arg1.getNumber();
            if (difference != 0) {
                return difference;
            }
            if (arg0.isNegated() && !arg1.isNegated()) {
                return 1;
            } else if (!arg0.isNegated() && arg1.isNegated()) {
                return -1;
            } else {
                return 0;
            }
        }

    }

    /**
     * Constructor taking the operator and the right hand side as its parameters.
     *
     * @param rightHandSide The right hand side.
     */
    public BooleanCalculation(final String rightHandSide) {
        String[] operandStrings =
                Arrays.stream(rightHandSide.split(getOperator().getSymbol())).map(String::trim).toArray(String[]::new);
        for (String operandString : operandStrings) {
            addOperand(new BooleanOperand(operandString));
        }
    }

    /**
     * Constructor taking a list of operands as the parameter.
     *
     * @param operands The list of operands.
     */
    public BooleanCalculation(final Collection<BooleanOperand> operands) {
        this.operands = new ModifiableArrayCollection<BooleanOperand>(operands);
    }

    /**
     * Adds an operand to the Boolean calculation.
     *
     * @param operandToBeAdded The operand to be added to the Boolean calculation.
     */
    protected void addOperand(final BooleanOperand operandToBeAdded) {
        operands.add(operandToBeAdded);
    }

    /**
     * Adds a list of operands to the Boolean calculation.
     *
     * @param operandsToBeAdded The operands to be added to the Boolean calculation.
     */
    protected void addOperands(final Collection<BooleanOperand> operandsToBeAdded) {
        operands.addAll(operandsToBeAdded);
    }

    /**
     * Expands operands as part of resolving the Boolean calculation.
     *
     * @param booleanFunction The Boolean function giving the context for the Boolean calculation.
     */
    protected void expandOperands(final BooleanFunction booleanFunction) {
        ModifiableCollection<BooleanOperand> expandedOperands = new ModifiableArrayCollection<BooleanOperand>();
        ModifiableCollection<BooleanOperand> expansions = new ModifiableArrayCollection<BooleanOperand>();
        for (BooleanOperand operand : getOperands()) {
            BooleanExpression be = booleanFunction.getExpression(operand.getName());
            if (be != null) {
                if (be instanceof BooleanEqualityConstraint) {
                    expansions.add(new BooleanOperand(be.getInputParameters().getAt(0).getName(), operand.isNegated()));
                    expandedOperands.add(operand);
                } else if (be instanceof BooleanOppositionConstraint) {
                    expansions
                            .add(new BooleanOperand(be.getInputParameters().getAt(0).getName(), !operand.isNegated()));
                    expandedOperands.add(operand);
                } else if (be instanceof BooleanOperation) {
                    BooleanRightHandSide rhs = ((BooleanOperation) be).getRightHandSide();
                    if (rhs instanceof BooleanEquation) {
                        BooleanEquation bq = (BooleanEquation) rhs;
                        expansions.add(new BooleanOperand(bq.getOperand(), operand.isNegated() ^ bq.isNegated()));
                        expandedOperands.add(operand);
                    } else if (rhs instanceof BooleanCalculation && !operand.isNegated()
                            && getOperator() == rhs.getOperator()) {
                        BooleanCalculation bc = (BooleanCalculation) rhs;
                        for (BooleanOperand bo : bc.getOperands()) {
                            expansions.add(new BooleanOperand(bo));
                        }
                        expandedOperands.add(operand);
                    }
                }
            }
        }
        removeOperands(expandedOperands);
        addOperands(expansions);
    }

    /**
     * Exports the Boolean calculation to a string.
     *
     * @param operatorString      The string to be used to represent the operator.
     * @param operandExportMethod The method to be used to export the operands.
     * @return A string representing the Boolean calculation.
     */
    private String exportToString(final String operatorString,
            final Function<BooleanOperand, String> operandExportMethod) {
        SortedCollection<BooleanOperand> sortedOperands =
                new SortedArrayCollection<BooleanOperand>(new OperandComparator(), operands);
        return String.join(" " + operatorString + " ",
                sortedOperands.stream().map(operandExportMethod).collect(Collectors.toOrderedCollection()));
    }

    @Override
    public final OrderedCollection<InputParameter> getInputParameters() {
        return operands.stream().filter(new Predicate<BooleanOperand>() {
            @Override
            public boolean test(final BooleanOperand operand) {
                return InputParameter.isInputParameter(operand.getName());
            }
        }).map(BooleanOperand::getName).map(InputParameter::get).collect(Collectors.toOrderedCollection());
    }

    @Override
    public final OrderedCollection<InternalVariable> getInternalVariables() {
        return operands.stream().filter(new Predicate<BooleanOperand>() {
            @Override
            public boolean test(final BooleanOperand operand) {
                return !InputParameter.isInputParameter(operand.getName());
            }
        }).map(BooleanOperand::getName).map(InternalVariable::get).collect(Collectors.toOrderedCollection());
    }

    /**
     * Returns the number of operands in the Boolean calculation.
     *
     * @return The number of operands in the Boolean calculation.
     */
    protected int getNumberOfOperands() {
        return operands.size();
    }

    /**
     * Returns an unmodifiable list of operands.
     *
     * @return An unmodifiable list of operands.
     */
    protected Collection<BooleanOperand> getOperands() {
        return new ArrayCollection<BooleanOperand>(operands);
    }

    @Override
    protected final boolean isFalse() {
        return false;
    }

    @Override
    protected final boolean isTrue() {
        return false;
    }

    /**
     * Remove an operand from the Boolean calculation.
     *
     * @param operandToBeRemoved The operand to be removed from the Boolean calculation.
     */
    protected void removeOperand(final BooleanOperand operandToBeRemoved) {
        operands.remove(operandToBeRemoved);
    }

    /**
     * Removes a list of operands from the Boolean calculation.
     *
     * @param operandsToBeRemoved The operands to be removed from the Boolean calculation.
     */
    protected void removeOperands(final Collection<BooleanOperand> operandsToBeRemoved) {
        operands.removeAll(operandsToBeRemoved);
    }

    @Override
    public final String toJavaString() {
        return exportToString(getOperator().getJavaSymbol(), BooleanOperand::toJavaString);
    }

    @Override
    public final String toString() {
        return exportToString(getOperator().getSymbol(), BooleanOperand::toString);
    }
}
