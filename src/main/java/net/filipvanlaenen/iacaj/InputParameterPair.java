package net.filipvanlaenen.iacaj;

/**
 * Class representing a pair of input parameters.
 */
public final class InputParameterPair {
    /**
     * The input parameter with the lowest number.
     */
    private final InputParameter firstInputParameter;
    /**
     * The input parameter with the highest number.
     */
    private final InputParameter lastInputParameter;

    /**
     * Constructor taking two input parameters as parameters. The input parameters
     * do not need to be provided in the right order.
     *
     * @param inputParameter1 An input parameter.
     * @param inputParameter2 An input parameter.
     */
    InputParameterPair(final InputParameter inputParameter1, final InputParameter inputParameter2) {
        // Replacing less-than with less-than-or-equal-to produces an equivalent mutant.
        if (inputParameter1.getNumber() < inputParameter2.getNumber()) {
            firstInputParameter = inputParameter1;
            lastInputParameter = inputParameter2;
        } else {
            firstInputParameter = inputParameter2;
            lastInputParameter = inputParameter1;
        }
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof InputParameterPair) {
            InputParameterPair other = (InputParameterPair) object;
            return firstInputParameter.equals(other.firstInputParameter)
                    && lastInputParameter.equals(other.lastInputParameter);
        } else {
            return false;
        }
    }

    /**
     * Returns the input parameter with the lowest number.
     *
     * @return The input parameter with the lowest number.
     */
    InputParameter getFirst() {
        return firstInputParameter;
    }

    /**
     * Returns the input parameter with the highest number.
     *
     * @return The input parameter with the highest number.
     */
    InputParameter getLast() {
        return lastInputParameter;
    }

    @Override
    public int hashCode() {
        return firstInputParameter.hashCode() + lastInputParameter.hashCode();
    }
}
