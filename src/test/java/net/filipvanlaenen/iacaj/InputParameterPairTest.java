package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>InputParameterPair</code> class.
 */
public class InputParameterPairTest {
    /**
     * The input parameter <code>i1</code>.
     */
    private static final InputParameter INPUT_PARAMETER_1 = InputParameter.get("i1");
    /**
     * The input parameter <code>i2</code>.
     */
    private static final InputParameter INPUT_PARAMETER_2 = InputParameter.get("i2");

    /**
     * <code>getFirst</code> returns the input parameter with the lowest number.
     */
    @Test
    public void getFirstShouldReturnTheInputParameterWithTheLowestNumber() {
        InputParameterPair pair = new InputParameterPair(INPUT_PARAMETER_1, INPUT_PARAMETER_2);
        assertEquals(INPUT_PARAMETER_1, pair.getFirst());
    }

    /**
     * <code>getFirst</code> returns the input parameter with the lowest number even
     * if they are provided in the other other to the constructor.
     */
    @Test
    public void getFirstShouldReturnTheInputParameterWithTheLowestNumberRegardlessOfParameterOrderInConstructor() {
        InputParameterPair pair = new InputParameterPair(INPUT_PARAMETER_2, INPUT_PARAMETER_1);
        assertEquals(INPUT_PARAMETER_1, pair.getFirst());
    }

    /**
     * <code>getLast</code> returns the input parameter with the lowest number.
     */
    @Test
    public void getLastShouldReturnTheInputParameterWithTheHighestNumber() {
        InputParameterPair pair = new InputParameterPair(INPUT_PARAMETER_1, INPUT_PARAMETER_2);
        assertEquals(INPUT_PARAMETER_2, pair.getLast());
    }
}
