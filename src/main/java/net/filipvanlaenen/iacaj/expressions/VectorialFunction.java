package net.filipvanlaenen.iacaj.expressions;

import java.util.stream.Collectors;

import net.filipvanlaenen.kolektoj.Map;

/**
 * Class representing vectorial Boolean functions. A vectorial Boolean function is a set of Boolean expressions mapped
 * to variables.
 */
public final class VectorialFunction {
    /**
     * A map mapping the variables to their expressions.
     */
    private final Map<Variable, Expression> variableToExpressionMap;

    /**
     * Constructs a vectorial function from a map of variables and expressions.
     *
     * @param variableToExpressionMap A map with the variables and their expressions.
     */
    public VectorialFunction(final Map<Variable, Expression> variableToExpressionMap) {
        this.variableToExpressionMap = Map.of(variableToExpressionMap);
    }

    @Override
    public String toString() {
        return variableToExpressionMap.stream().map(entry -> entry.key() + " = " + entry.value())
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
