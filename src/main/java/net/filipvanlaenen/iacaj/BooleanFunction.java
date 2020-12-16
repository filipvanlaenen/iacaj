package net.filipvanlaenen.iacaj;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class representing a (multi-dimensional) Boolean function.
 */
public class BooleanFunction {
    /**
     * The Boolean expressions of the Boolean function.
     */
    private Set<BooleanExpression> expressions = new HashSet<BooleanExpression>();
    /**
     * The input parameters of the Boolean function.
     */
    private Set<InputParameter> inputParameters = new HashSet<InputParameter>();

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
        inputParameters.addAll(booleanExpression.getInputParameters());
    }

    /**
     * Exports the Boolean function to a string. The type of string is controlled by
     * the export method provided to export the Boolean expression.
     *
     * @param exportMethod The method to export the Boolean expressions to strings.
     * @return A string representation of the Boolean function.
     */
    private String exportToString(final Function<BooleanExpression, String> exportMethod) {
        List<BooleanExpression> sortedExpressions = new ArrayList<BooleanExpression>(expressions);
        sortedExpressions.sort(new Comparator<BooleanExpression>() {
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
        });
        List<String> expressionStrings = sortedExpressions.stream().map(exportMethod).collect(Collectors.toList());
        return String.join("\n", expressionStrings);
    }

    /**
     * Returns the input parameters of the Boolean function.
     *
     * @return The input parameters of the Boolean function.
     */
    public Set<InputParameter> getInputParameters() {
        return inputParameters;
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
