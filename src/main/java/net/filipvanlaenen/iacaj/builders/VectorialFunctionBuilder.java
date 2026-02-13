package net.filipvanlaenen.iacaj.builders;

import net.filipvanlaenen.iacaj.expressions.AndFunction;
import net.filipvanlaenen.iacaj.expressions.Expression;
import net.filipvanlaenen.iacaj.expressions.IdentityExpression;
import net.filipvanlaenen.iacaj.expressions.LiteralExpression;
import net.filipvanlaenen.iacaj.expressions.Operator;
import net.filipvanlaenen.iacaj.expressions.OrFunction;
import net.filipvanlaenen.iacaj.expressions.Variable;
import net.filipvanlaenen.iacaj.expressions.VectorialFunction;
import net.filipvanlaenen.iacaj.expressions.XorFunction;
import net.filipvanlaenen.kolektoj.Map;
import net.filipvanlaenen.kolektoj.ModifiableMap;
import net.filipvanlaenen.kolektoj.OrderedCollection;
import net.filipvanlaenen.kolektoj.ValueCollection;

public abstract class VectorialFunctionBuilder {
    /**
     * The magic number 3.
     */
    private static final int THREE = 3;
    private String inputVectorName = "x";
    private String outputVectorName = "y";

    protected final class Word {
        private final OrderedCollection<Variable> variables;

        Word(final String inputVectorName, final Integer outputVectorWidth) {
            variables =
                    OrderedCollection.createSequence(i -> new Variable(inputVectorName + (i + 1)), outputVectorWidth);
        }

        private Word(final Word word, final int i, final int j) {
            variables = OrderedCollection.of(word.variables, i, j);
        }

        Word firstHalf() {
            return new Word(this, 0, variables.size() / 2);
        }

        Variable getAt(final int i) {
            return variables.getAt(i);
        }

        Word secondHalf() {
            return new Word(this, variables.size() / 2, variables.size());
        }

        int size() {
            return variables.size();
        }
    }

    abstract public VectorialFunction build() throws IllegalStateException;

    protected Map<Variable, Expression> buildAdditionFunctions(final Word inputVectorA, final Word inputVectorB,
            Word outputVector) {
        int width = outputVector.size();
        if (width != inputVectorA.size() || width != inputVectorB.size()) {
            throw new IllegalStateException(
                    "Input and output vectors should have the same width for an addition operation.");
        }
        ModifiableMap<Variable, Expression> map = ModifiableMap.empty();
        int n = width - 1;
        Variable ivan = inputVectorA.getAt(n);
        Variable ivbn = inputVectorB.getAt(n);
        map.add(outputVector.getAt(n), new XorFunction(ValueCollection.of(ivan, ivbn), false));
        if (width != 1) {
            int n1 = width - 2;
            Variable ovn1 = outputVector.getAt(n1);
            Variable carry = new Variable(ovn1.name() + "c");
            map.add(carry, new AndFunction(ValueCollection.of(ivan, ivbn), ValueCollection.empty()));
            map.add(ovn1,
                    new XorFunction(ValueCollection.of(inputVectorA.getAt(n1), inputVectorB.getAt(n1), carry), false));
            for (int i = width - THREE; i >= 0; i--) {
                Variable ivai1 = inputVectorA.getAt(i + 1);
                Variable ivbi1 = inputVectorB.getAt(i + 1);
                Variable ovi = outputVector.getAt(i);
                Variable p = new Variable(ovi.name() + "p");
                map.add(p, new XorFunction(ValueCollection.of(ivai1, ivbi1), false));
                Variable q = new Variable(ovi.name() + "q");
                map.add(q, new AndFunction(ValueCollection.of(carry, p), ValueCollection.empty()));
                Variable r = new Variable(ovi.name() + "r");
                map.add(r, new AndFunction(ValueCollection.of(ivai1, ivbi1), ValueCollection.empty()));
                carry = new Variable(ovi.name() + "c");
                map.add(carry, new XorFunction(ValueCollection.of(q, r), false));
                map.add(ovi, new XorFunction(ValueCollection.of(inputVectorA.getAt(i), inputVectorB.getAt(i), carry),
                        false));
            }
        }
        return map;
    }

    protected Map<Variable, Expression> buildOperationFunctions(final Word inputVectorA, final Word inputVectorB,
            final Word outputVector, Operator operator) {
        int width = outputVector.size();
        if (width != inputVectorA.size() || width != inputVectorB.size()) {
            throw new IllegalStateException(
                    "Input and output vectors should have the same width for an operator operation.");
        }
        ModifiableMap<Variable, Expression> map = ModifiableMap.empty();
        for (int i = 0; i < width; i++) {
            Variable ovi = outputVector.getAt(i);
            ValueCollection<Variable> inputVariables = ValueCollection.of(inputVectorA.getAt(i), inputVectorB.getAt(i));
            Expression expression = switch (operator) {
            case AND -> new AndFunction(inputVariables, ValueCollection.empty());
            case OR -> new OrFunction(inputVariables, ValueCollection.empty());
            case XOR -> new XorFunction(inputVariables, false);
            default -> null;
            };
            map.add(ovi, expression);
        }
        return map;
    }

    protected Map<Variable, Expression> buildRotationFunctions(final Word inputVector, final Word outputVector,
            final int rotateRight) {
        int width = outputVector.size();
        if (width != inputVector.size()) {
            throw new IllegalStateException(
                    "Input and output vector should have the same width for a rotation operation.");
        }
        ModifiableMap<Variable, Expression> map = ModifiableMap.empty();
        for (int i = 0; i < width; i++) {
            Variable ivi = inputVector.getAt(i);
            int j = (i + rotateRight + width) % width;
            Variable ovi = outputVector.getAt(j);
            map.add(ovi, new IdentityExpression(ivi));
        }
        return map;
    }

    protected Map<Variable, Expression> buildShiftFunctions(final Word inputVector, final Word outputVector,
            final int shiftRight) {
        int width = outputVector.size();
        if (width != inputVector.size()) {
            throw new IllegalStateException(
                    "Input and output vector should have the same width for a shift operation.");
        }
        ModifiableMap<Variable, Expression> map = ModifiableMap.empty();
        for (int i = 0; i < width; i++) {
            Variable ovi = outputVector.getAt(i);
            int j = i - shiftRight;
            if (j >= 0 && j < width) {
                Variable ivi = inputVector.getAt(j);
                map.add(ovi, new IdentityExpression(ivi));
            } else {
                map.add(ovi, LiteralExpression.FALSE);
            }
        }
        return map;
    }

    protected String getInputVectorName() {
        return inputVectorName;
    }

    protected String getOutputVectorName() {
        return outputVectorName;
    }

    public void inputVectorName(final String newInputVectorName) {
        this.inputVectorName = newInputVectorName;
    }

    public void outputVectorName(final String newOutputVectorName) {
        this.outputVectorName = newOutputVectorName;
    }

    protected void prebuild() throws IllegalStateException {
        if (inputVectorName == null) {
            throw new IllegalStateException("Cannot build a vectorial function when the input vector name is null.");
        }
        if (outputVectorName == null) {
            throw new IllegalStateException("Cannot build a vectorial function when the output vector name is null.");
        }
        if (inputVectorName.equals(outputVectorName)) {
            throw new IllegalStateException(
                    "Cannot build a vectorial  function when the input and output vector names are equal.");
        }
    }
}
