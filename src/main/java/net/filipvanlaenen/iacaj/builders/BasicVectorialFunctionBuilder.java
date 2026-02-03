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

public final class BasicVectorialFunctionBuilder extends VariableWidthVectorialFunctionBuilder {
    private Operator operator;

    @Override
    public VectorialFunction build() throws IllegalStateException {
        prebuild();
        if (operator == null) {
            throw new IllegalStateException("Cannot build a basic vectorial function when the operator isn't set.");
        }
        String inputVectorName = getInputVectorName();
        String outputVectorName = getOutputVectorName();
        Integer outputVectorWidth = getOutputVectorWidth();
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

    public void operator(Operator operator) {
        this.operator = operator;
    }
}
