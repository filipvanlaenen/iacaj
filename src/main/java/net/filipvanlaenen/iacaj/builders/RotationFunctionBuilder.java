package net.filipvanlaenen.iacaj.builders;

import net.filipvanlaenen.iacaj.expressions.Expression;
import net.filipvanlaenen.iacaj.expressions.IdentityExpression;
import net.filipvanlaenen.iacaj.expressions.Variable;
import net.filipvanlaenen.iacaj.expressions.VectorialFunction;
import net.filipvanlaenen.kolektoj.Map;
import net.filipvanlaenen.kolektoj.ModifiableMap;
import net.filipvanlaenen.kolektoj.OrderedCollection;

public final class RotationFunctionBuilder extends VariableWidthVectorialFunctionBuilder {
    private class Word {
        private final OrderedCollection<Variable> variables;

        Word(String inputVectorName, Integer outputVectorWidth) {
            variables =
                    OrderedCollection.createSequence(i -> new Variable(inputVectorName + (i + 1)), outputVectorWidth);
        }

        Variable getAt(int i) {
            return variables.getAt(i);
        }

        int size() {
            return variables.size();
        }
    }

    private Integer rotateRight;

    @Override
    public VectorialFunction build() throws IllegalStateException {
        prebuild();
        String inputVectorName = getInputVectorName();
        String outputVectorName = getOutputVectorName();
        Integer outputVectorWidth = getOutputVectorWidth();
        if (outputVectorWidth == null) {
            throw new IllegalStateException("Cannot build a rotate function when the output vector width isn't set.");
        }
        if (rotateRight == null) {
            throw new IllegalStateException("Cannot build a rotate function when the rotate right isn't set.");
        }
        ModifiableMap<Variable, Expression> map = ModifiableMap.empty();
        Word inputVector = new Word(inputVectorName, outputVectorWidth);
        Word outputVector = new Word(outputVectorName, outputVectorWidth);
        map.addAll(createRotation(inputVector, outputVector));
        return new VectorialFunction(map);
    }

    private Map<Variable, Expression> createRotation(Word inputVector, Word outputVector) {
        int width = outputVector.size();
        if (width != inputVector.size()) {
            throw new IllegalStateException(
                    "Input and output vector should have the same width for a rotation operation.");
        }
        ModifiableMap<Variable, Expression> map = ModifiableMap.empty();
        for (int i = 0; i < width; i++) {
            Variable ivi = inputVector.getAt(i);
            int j = (i + rotateRight) % width;
            Variable ovi = outputVector.getAt(j);
            map.add(ovi, new IdentityExpression(ivi));
        }
        return map;
    }

    public void rotateRight(Integer rotateRight) {
        this.rotateRight = rotateRight;
    }
}
