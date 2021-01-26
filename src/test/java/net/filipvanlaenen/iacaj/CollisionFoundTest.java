package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class CollisionFoundTest {
    private Set<InputParameter> createFourInputParameters() {
        Set<InputParameter> inputParameters = new HashSet<InputParameter>();
        for (int i = 0; i < 4; i++) {
            inputParameters.add(InputParameter.get("i" + i));
        }
        return inputParameters;
    }

    @Test
    public void toStringIsCorrectForOneConstraintOneEliminatedBitAndTwoFreeInputBits() {
        String[] content = new String[] {"i1 = True", "o1 = i3 ∧ i4"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        Set<InputParameter> inputParameters = createFourInputParameters();
        CollisionFound collisionFound = new CollisionFound(booleanFunction, inputParameters);
        StringBuffer expected = new StringBuffer();
        expected.append("Collision found:\n");
        expected.append("  Constraint:\n");
        expected.append("    i1 = True\n");
        expected.append("  Eliminated input bit: i2\n");
        expected.append("  Free input bits: i3, i4");
        assertEquals(expected.toString(), collisionFound.toString());
    }

    @Test
    public void toStringIsCorrectForTwoConstraintsOneEliminatedBitAndOneFreeInputBits() {
        String[] content = new String[] {"i1 = True", "i2 = True", "o1 = i4"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        Set<InputParameter> inputParameters = createFourInputParameters();
        CollisionFound collisionFound = new CollisionFound(booleanFunction, inputParameters);
        StringBuffer expected = new StringBuffer();
        expected.append("Collision found:\n");
        expected.append("  Constraints:\n");
        expected.append("    i1 = True\n");
        expected.append("    i2 = True\n");
        expected.append("  Eliminated input bit: i3\n");
        expected.append("  Free input bits: i4");
        assertEquals(expected.toString(), collisionFound.toString());
    }

    @Test
    public void toStringIsCorrectForOneConstraintTwoEliminatedBitsAndOneFreeInputBits() {
        String[] content = new String[] {"i1 = True", "o1 = i4"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        Set<InputParameter> inputParameters = createFourInputParameters();
        CollisionFound collisionFound = new CollisionFound(booleanFunction, inputParameters);
        StringBuffer expected = new StringBuffer();
        expected.append("Collision found:\n");
        expected.append("  Constraints:\n");
        expected.append("    i1 = True\n");
        expected.append("  Eliminated input bit: i2, i3\n");
        expected.append("  Free input bits: i4");
        assertEquals(expected.toString(), collisionFound.toString());
    }

    @Test
    public void toStringIsCorrectForOneConstraintTwoEliminatedBitsAndNoFreeInputBits() {
        String[] content = new String[] {"i1 = True", "o1 = True"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        Set<InputParameter> inputParameters = createFourInputParameters();
        CollisionFound collisionFound = new CollisionFound(booleanFunction, inputParameters);
        StringBuffer expected = new StringBuffer();
        expected.append("Collision found:\n");
        expected.append("  Constraints:\n");
        expected.append("    i1 = True\n");
        expected.append("  Eliminated input bit: i2, i3, i4\n");
        expected.append("  Free input bits: none");
        assertEquals(expected.toString(), collisionFound.toString());
    }
}
