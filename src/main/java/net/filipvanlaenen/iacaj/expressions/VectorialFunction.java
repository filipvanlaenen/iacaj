package net.filipvanlaenen.iacaj.expressions;

import java.util.stream.Collectors;

import net.filipvanlaenen.kolektoj.Map;
import net.filipvanlaenen.kolektoj.ModifiableMap;

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
            Expression expression = oldMap.get(variable).simplify(oldMap);
            map.add(variable, expression);
            for (Variable v : expression.getVariables()) {
                recursivelyAdd(map, v, oldMap);
            }
        }
    }

    public VectorialFunction simplify(final Word outputVector) {
        Map<Variable, Expression> map = variableToExpressionMap;
        boolean simplified = true;
        while (simplified) {
            System.out.println("Simplifying a vectorial Boolean function of dimension " + map.size() + "...");
            ModifiableMap<Variable, Expression> newMap = ModifiableMap.empty();
            for (Variable variable : outputVector.getVariables()) {
                recursivelyAdd(newMap, variable, map);
            }
            simplified = newMap.size() < map.size();
            map = newMap;
        }
        return new VectorialFunction(map);
    }

    @Override
    public String toString() {
        return variableToExpressionMap.stream().map(entry -> entry.key() + " = " + entry.value())
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
