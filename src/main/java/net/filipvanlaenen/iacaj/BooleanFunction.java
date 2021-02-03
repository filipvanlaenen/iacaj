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

/**
 * Class representing a (multi-dimensional) Boolean function.
 */
public class BooleanFunction {
    /**
     * Comparator used to sort the Boolean expressions when exporting the Boolean
     * function. Input parameters should come first, followed by internal variables,
     * and output parameters at the end. Each group should internally be sorted
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
         * Compares two Boolean constraints for sorting. The comparison is done based on
         * the numbers of the constraints.
         *
         * @param constraint0 The first Boolean constraint.
         * @param constraint1 The second Boolean constraint.
         * @return The result of the comparison.
         */
        private int compare(final BooleanConstraint constraint0, final BooleanConstraint constraint1) {
            return constraint0.getNumber() - constraint1.getNumber();
        }

        /**
         * Compares two Boolean operations for sorting. Output parameters are always
         * larger than internal variables, otherwise the comparison is done based on the
         * numbers.
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
    private Set<BooleanExpression> expressions = new HashSet<BooleanExpression>();
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
    private Set<InputParameter> inputParameters = new HashSet<InputParameter>();

    public BooleanFunction() {
    }

    public BooleanFunction(BooleanFunction parent) {
        this();
        for (BooleanExpression expression : parent.getExpressions()) {
            addExpression(BooleanExpression.parse(expression.toString())); // TODO
        }
    }

    /**
     * Factory method creating a BooleanFunction instance based on an array of
     * strings.
     *
     * @param content An array of strings representing a Boolean function.
     * @return A Boolean function.
     */
    public static BooleanFunction parse(final String[] content) {
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
     * Exports the Boolean function to a string. The type of string is controlled by
     * the export method provided to export the Boolean expression.
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
    public Set<BooleanConstraint> getConstraints() {
        return Set.copyOf(constraints);
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

    private Set<BooleanExpression> getExpressions() {
        return Set.copyOf(expressions);
    }

    /**
     * Returns the input parameters of the Boolean function.
     *
     * @return The input parameters of the Boolean function.
     */
    public Set<InputParameter> getInputParameters() {
        return Set.copyOf(inputParameters);
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
     * Calculates the number of constraints in the Boolean function.
     *
     * @return The number of constraints in the Boolean function.
     */
    int getNumberOfConstraints() {
        return constraints.size();
    }

    public int getNumberOfInputParameters() {
        return inputParameters.size();
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
    private List<BooleanExpression> getSortedExpressions() {
        List<BooleanExpression> sortedExpressions = new ArrayList<BooleanExpression>(expressions);
        sortedExpressions.sort(new BooleanExpressionComparator());
        return sortedExpressions;
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
        for (BooleanExpression expression : expressions) {
            inputParameters.addAll(expression.getInputParameters());
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
