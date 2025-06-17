package net.filipvanlaenen.iacaj;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.filipvanlaenen.kolektoj.Collection;
import net.filipvanlaenen.kolektoj.Collection.ElementCardinality;
import net.filipvanlaenen.kolektoj.ModifiableCollection;
import net.filipvanlaenen.kolektoj.SortedCollection;
import net.filipvanlaenen.kolektoj.array.ArrayCollection;
import net.filipvanlaenen.kolektoj.array.SortedArrayCollection;

/**
 * Class representing a (multi-dimensional) Boolean function.
 */
public class BooleanFunction {
    /**
     * Comparator used to sort the Boolean expressions when exporting the Boolean function. Input parameters should come
     * first, followed by internal variables, and output parameters at the end. Each group should internally be sorted
     * ascending according to the variable's number.
     */
    static final class BooleanExpressionComparator implements Comparator<BooleanExpression> {
        @Override
        public int compare(final BooleanExpression expression0, final BooleanExpression expression1) {
            if (expression0 instanceof BooleanConstraint) {
                if (expression1 instanceof BooleanConstraint) {
                    return compare((BooleanConstraint) expression0, (BooleanConstraint) expression1);
                } else {
                    return -1;
                }
            } else if (expression1 instanceof BooleanConstraint) {
                return 1;
            } else {
                return compare((BooleanOperation) expression0, (BooleanOperation) expression1);
            }
        }

        /**
         * Compares two Boolean constraints for sorting. The comparison is done based on the numbers of the constraints.
         *
         * @param constraint0 The first Boolean constraint.
         * @param constraint1 The second Boolean constraint.
         * @return The result of the comparison.
         */
        private int compare(final BooleanConstraint constraint0, final BooleanConstraint constraint1) {
            return constraint0.getNumber() - constraint1.getNumber();
        }

        /**
         * Compares two Boolean operations for sorting. Output parameters are always larger than internal variables,
         * otherwise the comparison is done based on the numbers.
         *
         * @param operation0 The first Boolean operation.
         * @param operation1 The second Boolean operation.
         * @return The result of the comparison.
         */
        public int compare(final BooleanOperation operation0, final BooleanOperation operation1) {
            if (operation0.isOutputParameter()) {
                if (operation1.isOutputParameter()) {
                    return operation0.getNumber() - operation1.getNumber();
                } else {
                    return 1;
                }
            } else if (operation1.isOutputParameter()) {
                return -1;
            } else {
                return operation0.getNumber() - operation1.getNumber();
            }
        }
    }

    /**
     * The Boolean expressions of the Boolean function.
     */
    private ModifiableCollection<BooleanExpression> expressions = ModifiableCollection.of();
    /**
     * The Boolean constraints of the Boolean function.
     */
    private Set<BooleanConstraint> constraints = new HashSet<BooleanConstraint>();
    /**
     * A map with the Boolean expressions of the Boolean function.
     */
    private Map<String, BooleanExpression> expressionMap = new HashMap<String, BooleanExpression>();
    /**
     * The input parameters of the Boolean function.
     */
    private ModifiableCollection<InputParameter> inputParameters =
            ModifiableCollection.<InputParameter>of(ElementCardinality.DISTINCT_ELEMENTS);
    /**
     * The input parameters of the Boolean function being used in the calculation.
     */
    private ModifiableCollection<InputParameter> inputParametersInCalculation =
            ModifiableCollection.<InputParameter>of(ElementCardinality.DISTINCT_ELEMENTS);

    /**
     * Constructor to create an empty Boolean function.
     */
    public BooleanFunction() {
    }

    /**
     * Constructor copying the expressions from another Boolean function.
     *
     * @param prototype The Boolean function to copy the expressions from.
     */
    public BooleanFunction(final BooleanFunction prototype) {
        this();
        for (BooleanExpression expression : prototype.getExpressions()) {
            addExpression(expression.deepClone());
        }
    }

    /**
     * Constructor copying the expression from another Boolean function and adding the constraints to it.
     *
     * @param prototype   The Boolean function to copy the expressions from.
     * @param constraints The constraints to be added to the Boolean function.
     */
    public BooleanFunction(final BooleanFunction prototype, final BooleanConstraints constraints) {
        this(prototype);
        for (BooleanConstraint constraint : constraints.getConstraints()) {
            addExpression(constraint.deepClone());
        }
    }

    /**
     * Factory method creating a BooleanFunction instance based on an array of strings.
     *
     * @param content An array of strings representing a Boolean function.
     * @return A Boolean function.
     */
    public static BooleanFunction parse(final String... content) {
        BooleanFunction result = new BooleanFunction();
        for (String line : content) {
            BooleanExpression expression = BooleanExpression.parse(line);
            if (expression != null) {
                result.addExpression(expression);
            }
        }
        return result;
    }

    /**
     * Adds an expression to the Boolean function.
     *
     * @param booleanExpression A Boolean expression.
     */
    public void addExpression(final BooleanExpression booleanExpression) {
        expressions.add(booleanExpression);
        expressionMap.put(booleanExpression.getName(), booleanExpression);
        inputParameters.addAll(booleanExpression.getInputParameters());
        if (booleanExpression instanceof BooleanConstraint) {
            constraints.add((BooleanConstraint) booleanExpression);
        } else {
            inputParametersInCalculation.addAll(booleanExpression.getInputParameters());
        }
    }

    /**
     * Adds a set of expressions to the Boolean function.
     *
     * @param booleanExpressions A set of expressions.
     */
    public void addExpressions(final Set<BooleanExpression> booleanExpressions) {
        for (BooleanExpression booleanExpression : booleanExpressions) {
            addExpression(booleanExpression);
        }
    }

    /**
     * Exports the Boolean function to a string. The type of string is controlled by the export method provided to
     * export the Boolean expression.
     *
     * @param exportMethod The method to export the Boolean expressions to strings.
     * @return A string representation of the Boolean function.
     */
    private String exportToString(final Function<BooleanExpression, String> exportMethod) {
        List<String> expressionStrings = getSortedExpressions().stream().map(exportMethod).collect(Collectors.toList());
        return String.join("\n", expressionStrings);
    }

    /**
     * Returns the constraints of the Boolean function.
     *
     * @return The constraints of the Boolean function.
     */
    public BooleanConstraints getConstraints() {
        return new BooleanConstraints(constraints);
    }

    /**
     * Returns the Boolean expression with the given name.
     *
     * @param name The name of the requested Boolean expression.
     * @return The Boolean expression with the given name.
     */
    public BooleanExpression getExpression(final String name) {
        return expressionMap.get(name);
    }

    /**
     * Returns the expressions of the Boolean function as a new set.
     *
     * @return A new set with all the expressions of the Boolean function.
     */
    Collection<BooleanExpression> getExpressions() {
        return new ArrayCollection<BooleanExpression>(expressions);
    }

    /**
     * Returns the input parameters of the Boolean function.
     *
     * @return The input parameters of the Boolean function.
     */
    public Collection<InputParameter> getInputParameters() {
        return Collection.of(inputParameters);
    }

    /**
     * Returns the input parameters of the Boolean function used in the calculation.
     *
     * @return The input parameters of the Boolean function used in the calculation.
     */
    public Collection<InputParameter> getInputParametersInCalculation() {
        return Collection.of(inputParametersInCalculation);
    }

    /**
     * Returns the number of Boolean expressions in the Boolean function.
     *
     * @return The number of Boolean expressions in the Boolean function.
     */
    public int getNumberOfBooleanExpressions() {
        return expressions.size() - getNumberOfConstraints();
    }

    /**
     * Returns the number of constraints in the Boolean function.
     *
     * @return The number of constraints in the Boolean function.
     */
    int getNumberOfConstraints() {
        return constraints.size();
    }

    /**
     * Returns the number of input parameters of the Boolean function.
     *
     * @return The number of input parameters of the Boolean function.
     */
    int getNumberOfInputParameters() {
        return inputParameters.size();
    }

    /**
     * Returns the number of input parameters of the Boolean function used in the calculation.
     *
     * @return The number of input parameters of the Boolean function used in the calculation.
     */
    int getNumberOfInputParametersInCalculation() {
        return inputParametersInCalculation.size();
    }

    /**
     * Returns the Boolean constraints in sorted order.
     *
     * @return A sorted list with the Boolean constraints.
     */
    List<BooleanConstraint> getSortedConstraints() {
        List<BooleanConstraint> sortedConstraints = new ArrayList<BooleanConstraint>(constraints);
        sortedConstraints.sort(new BooleanExpressionComparator());
        return sortedConstraints;
    }

    /**
     * Returns the Boolean expressions in sorted order.
     *
     * @return A sorted list with the Boolean expressions.
     */
    private SortedCollection<BooleanExpression> getSortedExpressions() {
        return new SortedArrayCollection<BooleanExpression>(new BooleanExpressionComparator(), expressions);
    }

    /**
     * Resolves a Boolean function.
     */
    public void resolve() {
        Set<String> referencedInternalVariables = new HashSet<String>();
        for (BooleanExpression expression : getSortedExpressions()) {
            expression.resolve(this);
            for (InternalVariable iv : expression.getInternalVariables()) {
                referencedInternalVariables.add(iv.getName());
            }
        }
        int before;
        do {
            before = expressions.size();
            expressions.removeIf(new Predicate<BooleanExpression>() {
                @Override
                public boolean test(final BooleanExpression expression) {
                    return InternalVariable.isInternalVariable(expression.getName())
                            && !referencedInternalVariables.contains(expression.getName());
                }
            });
            referencedInternalVariables.clear();
            for (BooleanExpression expression : getSortedExpressions()) {
                for (InternalVariable iv : expression.getInternalVariables()) {
                    referencedInternalVariables.add(iv.getName());
                }
            }
        } while (expressions.size() != before);
        inputParameters.clear();
        inputParametersInCalculation.clear();
        for (BooleanExpression expression : expressions) {
            inputParameters.addAll(expression.getInputParameters());
            if (expression instanceof BooleanOperation) {
                inputParametersInCalculation.addAll(expression.getInputParameters());
            }
        }
    }

    /**
     * Exports the Boolean function to a string with Java code.
     *
     * @return A string representing the Boolean function in Java code.
     */
    public String toJavaString() {
        return exportToString(BooleanExpression::toJavaString);
    }

    /**
     * Exports the Boolean function to a string.
     */
    @Override
    public String toString() {
        return exportToString(BooleanExpression::toString);
    }
}
