package net.filipvanlaenen.iacaj.builders;

import net.filipvanlaenen.iacaj.expressions.AndFunction;
import net.filipvanlaenen.iacaj.expressions.Expression;
import net.filipvanlaenen.iacaj.expressions.Operator;
import net.filipvanlaenen.iacaj.expressions.OrFunction;
import net.filipvanlaenen.iacaj.expressions.Variable;
import net.filipvanlaenen.iacaj.expressions.VectorialFunction;
import net.filipvanlaenen.iacaj.expressions.XorFunction;
import net.filipvanlaenen.kolektoj.ModifiableMap;
import net.filipvanlaenen.kolektoj.ValueCollection;

final class BasicVectorialFunctionBuilder {
    private String inputVectorName = "x";
    private String outputVectorName = "y";
    private Integer outputVectorWidth;
    private Operator operator;

    VectorialFunction build() throws IllegalStateException {
        if (operator == null) {
            throw new IllegalStateException("Cannot build a basic vectorial function when the operator isn't set.");
        }
        if (inputVectorName == null) {
            throw new IllegalStateException(
                    "Cannot build a vectorial " + operator.name() + " function when the input vector name is null.");
        }
        if (outputVectorName == null) {
            throw new IllegalStateException(
                    "Cannot build a vectorial " + operator.name() + " function when the output vector name is null.");
        }
        if (inputVectorName.equals(outputVectorName)) {
            throw new IllegalStateException("Cannot build a vectorial " + operator.name()
                    + " function when the input and output vector names are equal.");
        }
        if (outputVectorWidth == null) {
            throw new IllegalStateException("Cannot build a vectorial " + operator.name()
                    + " function when the output vector width isn't set.");
        }
        ModifiableMap<Variable, Expression> map = ModifiableMap.empty();
        for (int i = 1; i <= outputVectorWidth; i++) {
            Variable ovi = new Variable(outputVectorName + i);
            Variable ivai = new Variable(inputVectorName + i);
            Variable ivbi = new Variable(inputVectorName + (outputVectorWidth + i));
            ValueCollection<Variable> inputVariables = ValueCollection.of(ivai, ivbi);
            Expression expression = switch (operator) {
            case AND -> new AndFunction(inputVariables, ValueCollection.empty());
            case OR -> new OrFunction(inputVariables, ValueCollection.empty());
            case XOR -> new XorFunction(inputVariables, false);
            default -> null;
            };
            map.add(ovi, expression);
        }
        return new VectorialFunction(map);
    }

    void inputVectorName(String inputVectorName) {
        this.inputVectorName = inputVectorName;
    }

    void operator(Operator operator) {
        this.operator = operator;
    }

    void outputVectorName(String outputVectorName) {
        this.outputVectorName = outputVectorName;
    }

    void outputVectorWidth(int width) {
        this.outputVectorWidth = width;
    }
}
