package net.filipvanlaenen.iacaj.expressions;

import java.util.stream.Collectors;

import net.filipvanlaenen.kolektoj.Map;

public class VectorialFunction {
    private final Map<Variable, Expression> functions;

    public VectorialFunction(final Map<Variable, Expression> result) {
        functions = Map.of(result);
    }

    @Override
    public String toString() {
        return functions.stream().map(entry -> entry.key() + " = " + entry.value())
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
