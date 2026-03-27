package net.filipvanlaenen.iacaj.expressions;

import java.util.stream.Collectors;

import net.filipvanlaenen.kolektoj.Map;
import net.filipvanlaenen.kolektoj.ModifiableMap;
import net.filipvanlaenen.laconic.Laconic;

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

    public VectorialFunction extendWih(ModifiableMap<Variable, Expression> message) {
        // TODO: Simplify when https://github.com/filipvanlaenen/kolektoj/issues/100 has been implemented
        ModifiableMap<Variable, Expression> newMap = ModifiableMap.empty();
        newMap.addAll(variableToExpressionMap);
        newMap.addAll(message);
        return new VectorialFunction(newMap);
    }

    private void recursivelyAdd(ModifiableMap<Variable, Expression> map, Variable variable,
            Map<Variable, Expression> oldMap) {
        if (!map.containsKey(variable) && oldMap.containsKey(variable)) {
            Expression expression1 = oldMap.get(variable).simplify();
            Expression expression3 = expression1.simplify(oldMap);
            map.add(variable, expression3);
            for (Variable v : expression3.getVariables()) {
                recursivelyAdd(map, v, oldMap);
            }
        }
    }

    public VectorialFunction simplify(final Word outputVector) {
        Map<Variable, Expression> map = variableToExpressionMap;
        boolean simplified = true;
        while (simplified) {
            Laconic.LOGGER.logProgress("Simplifying a vectorial Boolean function of dimension %d...", map.size());
            ModifiableMap<Variable, Expression> newMap = ModifiableMap.empty();
            for (Variable variable : outputVector.getVariables()) {
                recursivelyAdd(newMap, variable, map);
            }
            simplified = newMap.size() < map.size();
            map = newMap;
        }
        Laconic.LOGGER.logProgress("The vectorial Boolean function was simplified to a dimension of %d.", map.size());
        return new VectorialFunction(map);
    }

    @Override
    public String toString() {
        return variableToExpressionMap.stream().map(entry -> entry.key() + " = " + entry.value())
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public boolean containsSame(VectorialFunction result) {
        return variableToExpressionMap.containsSame(result.variableToExpressionMap);
    }
}
