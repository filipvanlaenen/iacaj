package net.filipvanlaenen.iacaj.builders;

import net.filipvanlaenen.iacaj.expressions.AndFunction;
import net.filipvanlaenen.iacaj.expressions.Expression;
import net.filipvanlaenen.iacaj.expressions.IdentityExpression;
import net.filipvanlaenen.iacaj.expressions.LiteralExpression;
import net.filipvanlaenen.iacaj.expressions.NegationExpression;
import net.filipvanlaenen.iacaj.expressions.Operator;
import net.filipvanlaenen.iacaj.expressions.OrFunction;
import net.filipvanlaenen.iacaj.expressions.Variable;
import net.filipvanlaenen.iacaj.expressions.VectorialFunction;
import net.filipvanlaenen.iacaj.expressions.Word;
import net.filipvanlaenen.iacaj.expressions.XorFunction;
import net.filipvanlaenen.kolektoj.Map;
import net.filipvanlaenen.kolektoj.ModifiableMap;
import net.filipvanlaenen.kolektoj.ValueCollection;

/**
 * Abstract class for vectorial function builders.
 */
public abstract class VectorialFunctionBuilder {
    /**
     * The magic number 3.
     */
    private static final int THREE = 3;
    /**
     * The input vector name, default <code>x</code>.
     */
    private String inputVectorName = "x";
    /**
     * The output vector name, default <code>y</code>.
     */
    private String outputVectorName = "y";

    /**
     * Builds the vectorial function.
     *
     * @return The vectorial function as specified in the builder.
     * @throws IllegalStateException Thrown if the builder isn't in a state ready to build.
     */
    public abstract VectorialFunction build() throws IllegalStateException;

    /**
     * Builds a map with variables and Boolean expression adding two words together.
     *
     * @param inputVectorA The first word to add.
     * @param inputVectorB The second word to add.
     * @param outputVector The resulting word.
     * @return A map with variables and Boolean expression adding two words together.
     * @throws IllegalStateException Thrown if the words don't have the same size.
     */
    protected Map<Variable, Expression> buildAdditionFunctions(final Word inputVectorA, final Word inputVectorB,
            final Word outputVector) throws IllegalStateException {
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

    /**
     * Builds a map assigning a long value to a word.
     *
     * @param resultVector The word to which the number should be assigned.
     * @param value        The number to assign.
     * @return A map assigning a long value to a word.
     */
    protected static Map<Variable, Expression> buildAssignmentFunctions(final Word resultVector, final Long value) {
        int width = resultVector.size();
        long remainder = value;
        ModifiableMap<Variable, Expression> map = ModifiableMap.empty();
        for (int i = width - 1; i >= 0; i--) {
            Variable ri = resultVector.getAt(i);
            if (remainder % 2 == 0) {
                map.add(ri, LiteralExpression.FALSE);
            } else {
                map.add(ri, LiteralExpression.TRUE);
            }
            remainder = remainder / 2;
        }
        return map;
    }

    /**
     * Builds a map with variables and Boolean expressions implementing an identity operation between the input and the
     * output vector.
     *
     * @param inputVector  The word representing the input vector.
     * @param outputVector The word representing the output vector.
     * @return A map with variables and Boolean expressions implementing an identity operation between the input and the
     *         output vector.
     * @throws IllegalStateException Thrown if the words don't have the same size.
     */
    protected Map<Variable, Expression> buildIdentityFunctions(final Word inputVector, final Word outputVector) {
        int width = outputVector.size();
        if (width != inputVector.size()) {
            throw new IllegalStateException(
                    "Input and output vector should have the same width for a reassignment operation.");
        }
        ModifiableMap<Variable, Expression> map = ModifiableMap.empty();
        for (int i = 0; i < width; i++) {
            Variable ovi = outputVector.getAt(i);
            Variable ivi = inputVector.getAt(i);
            map.add(ovi, new IdentityExpression(ivi));
        }
        return map;
    }

    /**
     * Builds a map with variables and Boolean expressions implementing a negation operation between the input and the
     * output vector.
     *
     * @param inputVector  The word representing the input vector.
     * @param outputVector The word representing the output vector.
     * @return A map with variables and Boolean expressions implementing a negation operation between the input and the
     *         output vector.
     * @throws IllegalStateException Thrown if the words don't have the same size.
     */
    protected Map<Variable, Expression> buildNegationFunctions(final Word inputVector, final Word outputVector) {
        int width = outputVector.size();
        if (width != inputVector.size()) {
            throw new IllegalStateException(
                    "Input and output vector should have the same width for a reassignment operation.");
        }
        ModifiableMap<Variable, Expression> map = ModifiableMap.empty();
        for (int i = 0; i < width; i++) {
            Variable ovi = outputVector.getAt(i);
            Variable ivi = inputVector.getAt(i);
            map.add(ovi, new NegationExpression(ivi));
        }
        return map;
    }

    /**
     * Builds a map with variables and Boolean expressions combining words together with an operator.
     *
     * @param operator     The operator.
     * @param outputVector The resulting word.
     * @param inputVectors The words to combine.
     *
     * @return A map with variables and Boolean expression combining words together with an operator.
     * @throws IllegalStateException Thrown if the words don't have the same size.
     */
    protected Map<Variable, Expression> buildOperationFunctions(final Operator operator, final Word outputVector,
            final Word... inputVectors) {
        int width = outputVector.size();
        for (Word inputVector : inputVectors) {
            if (width != inputVector.size()) {
                throw new IllegalStateException(
                        "Input and output vectors should have the same width for an operator operation.");
            }
        }
        ModifiableMap<Variable, Expression> map = ModifiableMap.empty();
        for (int i = 0; i < width; i++) {
            Variable ovi = outputVector.getAt(i);
            Variable[] inputVariablesArray = new Variable[inputVectors.length];
            for (int j = 0; j < inputVectors.length; j++) {
                inputVariablesArray[j] = inputVectors[j].getAt(i);
            }
            ValueCollection<Variable> inputVariables = ValueCollection.of(inputVariablesArray);
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

    /**
     * Builds a map with variables and Boolean expression rotating a word into another.
     *
     * @param inputVector  The word to rotate.
     * @param rotateRight  The number of positions to rotate to the right.
     * @param outputVector The rotated word.
     * @return A map with variables and Boolean expression rotating a word into another.
     * @throws IllegalStateException Thrown if the words don't have the same size.
     */
    protected Map<Variable, Expression> buildRotationFunctions(final Word inputVector, final int rotateRight,
            final Word outputVector) {
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

    /**
     * Builds a map with variables and Boolean expression shifting a word into another.
     *
     * @param inputVector  The word to shift.
     * @param shiftRight   The number of positions to shift to the right.
     * @param outputVector The shifted word.
     * @return A map with variables and Boolean expression shifting a word into another.
     * @throws IllegalStateException Thrown if the words don't have the same size.
     */
    protected Map<Variable, Expression> buildShiftFunctions(final Word inputVector, final int shiftRight,
            final Word outputVector) {
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

    /**
     * Returns the name of the input vector.
     *
     * @return The name of the input vector.
     */
    protected String getInputVectorName() {
        return inputVectorName;
    }

    /**
     * Returns the name of the output vector.
     *
     * @return The name of the output vector.
     */
    public abstract Word getOutputVector();

    /**
     * Returns the name of the output vector.
     *
     * @return The name of the output vector.
     */
    protected String getOutputVectorName() {
        return outputVectorName;
    }

    /**
     * Sets the input vector name.
     *
     * @param newInputVectorName The input vector name.
     */
    public void inputVectorName(final String newInputVectorName) {
        this.inputVectorName = newInputVectorName;
    }

    /**
     * Sets the output vector name.
     *
     * @param newOutputVectorName The output vector name.
     */
    public void outputVectorName(final String newOutputVectorName) {
        this.outputVectorName = newOutputVectorName;
    }

    /**
     * Runs assertions before the build phase is performed, typically verifying the state of the builder.
     *
     * @throws IllegalStateException Thrown if the builder isn't in a state ready to build.
     */
    protected void prebuild() throws IllegalStateException {
        if (inputVectorName == null) {
            throw new IllegalStateException("Cannot build a vectorial function when the input vector name is null.");
        }
        if (outputVectorName == null) {
            throw new IllegalStateException("Cannot build a vectorial function when the output vector name is null.");
        }
        if (inputVectorName.equals(outputVectorName)) {
            throw new IllegalStateException(
                    "Cannot build a vectorial function when the input and output vector names are equal.");
        }
    }
}
