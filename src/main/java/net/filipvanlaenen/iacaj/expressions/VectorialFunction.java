package net.filipvanlaenen.iacaj.expressions;

import java.util.stream.Collectors;

import net.filipvanlaenen.kolektoj.Map;

public class VectorialFunction {
    private final Map<Variable, Expression> variableToFunctionMap;

    public VectorialFunction(final Map<Variable, Expression> variableToFunctionMap) {
        this.variableToFunctionMap = Map.of(variableToFunctionMap);
    }

    @Override
    public String toString() {
        return variableToFunctionMap.stream().map(entry -> entry.key() + " = " + entry.value())
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
