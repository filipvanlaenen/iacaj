package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AttackTest {
    /**
     * Verifies that when a Boolean doesn't use any input parameters, the attack
     * discovers this.
     */
    @Test
    public void shouldReturnNoInputParametersWhenTheBooleanFunctionDoesNotHaveInputParameters() {
        String[] content = new String[] {"o1 = True"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        Attack attack = new Attack(booleanFunction);
        assertTrue(attack.perform() instanceof NoInputParameters);
    }

    /**
     * Verifies that if all input parameters are eliminated after the initial
     * resolution, the attack discovers this.
     */
    @Test
    public void shouldReturnAllInputParametersEliminatedWhenInitialResolveResolvesThemAll() {
        String[] content = new String[] {"v1 = i1", "o1 = True"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        Attack attack = new Attack(booleanFunction);
        assertTrue(attack.perform() instanceof AllInputParametersEliminated);
    }

    /**
     * Verifies that if some input parameters are eliminated after the initial
     * resolution, the attack discovers this.
     */
    @Test
    public void shouldReturnSomeInputParametersEliminatedWhenInitialResolveResolvesSomeOfThem() {
        String[] content = new String[] {"v1 = i1", "o1 = i2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        Attack attack = new Attack(booleanFunction);
        assertTrue(attack.perform() instanceof SomeInputParametersEliminated);
    }
}
