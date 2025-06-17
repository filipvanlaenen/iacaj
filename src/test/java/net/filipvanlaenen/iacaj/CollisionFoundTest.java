package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.kolektoj.Collection;
import net.filipvanlaenen.kolektoj.ModifiableCollection;

/**
 * Unit tests on the <code>CollisionFound</code> class.
 */
public class CollisionFoundTest {
    /**
     * Magic number four.
     */
    private static final int FOUR = 4;

    /**
     * Creates a set of four input parameters.
     *
     * @return A set with four input parameters.
     */
    private Collection<InputParameter> createFourInputParameters() {
        ModifiableCollection<InputParameter> inputParameters = ModifiableCollection.<InputParameter>empty();
        for (int i = 1; i <= FOUR; i++) {
            inputParameters.add(InputParameter.get("i" + i));
        }
        return inputParameters;
    }

    /**
     * Verifies that <code>toString</code> produces a correct message when the collision has one constraint, one
     * eliminated input parameter and two free input input parameters.
     */
    @Test
    public void toStringIsCorrectForOneConstraintOneEliminatedParameterAndTwoFreeInputParameters() {
        String[] content = new String[] {"i1 = True", "o1 = i3 ∧ i4"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        Collection<InputParameter> inputParameters = createFourInputParameters();
        CollisionFound collisionFound = new CollisionFound(booleanFunction, inputParameters);
        StringBuffer expected = new StringBuffer();
        expected.append("Collision found:\n");
        expected.append("  Constraint:\n");
        expected.append("    i1 = True\n");
        expected.append("  Eliminated input parameter: i2\n");
        expected.append("  Free input parameters: i3, i4");
        assertEquals(expected.toString(), collisionFound.toString());
    }

    /**
     * Verifies that <code>toString</code> produces a correct message when the collision has two constraints, one
     * eliminated input parameter and one free input input parameter.
     */
    @Test
    public void toStringIsCorrectForTwoConstraintsOneEliminatedInputParameterAndOneFreeInputInputParameter() {
        String[] content = new String[] {"i1 = True", "i2 = True", "o1 = i4"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        Collection<InputParameter> inputParameters = createFourInputParameters();
        CollisionFound collisionFound = new CollisionFound(booleanFunction, inputParameters);
        StringBuffer expected = new StringBuffer();
        expected.append("Collision found:\n");
        expected.append("  Constraints:\n");
        expected.append("    i1 = True\n");
        expected.append("    i2 = True\n");
        expected.append("  Eliminated input parameter: i3\n");
        expected.append("  Free input parameter: i4");
        assertEquals(expected.toString(), collisionFound.toString());
    }

    /**
     * Verifies that <code>toString</code> produces a correct message when the collision has one constraint, two
     * eliminated input parameters and one free input parameter.
     */
    @Test
    public void toStringIsCorrectForOneConstraintTwoEliminatedInputParametersAndOneFreeInputParameter() {
        String[] content = new String[] {"i1 = True", "o1 = i4"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        Collection<InputParameter> inputParameters = createFourInputParameters();
        CollisionFound collisionFound = new CollisionFound(booleanFunction, inputParameters);
        StringBuffer expected = new StringBuffer();
        expected.append("Collision found:\n");
        expected.append("  Constraint:\n");
        expected.append("    i1 = True\n");
        expected.append("  Eliminated input parameters: i2, i3\n");
        expected.append("  Free input parameter: i4");
        assertEquals(expected.toString(), collisionFound.toString());
    }

    /**
     * Verifies that <code>toString</code> produces a correct message when the collision has one constraint, three
     * eliminated input parameters and no free input parameters.
     */
    @Test
    public void toStringIsCorrectForOneConstraintTwoEliminatedInputParametersAndNoFreeInputParameters() {
        String[] content = new String[] {"i1 = True", "o1 = True"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        Collection<InputParameter> inputParameters = createFourInputParameters();
        CollisionFound collisionFound = new CollisionFound(booleanFunction, inputParameters);
        StringBuffer expected = new StringBuffer();
        expected.append("Collision found:\n");
        expected.append("  Constraint:\n");
        expected.append("    i1 = True\n");
        expected.append("  Eliminated input parameters: i2, i3, i4\n");
        expected.append("  Free input parameters: none");
        assertEquals(expected.toString(), collisionFound.toString());
    }
}
