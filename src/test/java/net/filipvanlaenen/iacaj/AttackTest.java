package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>Attack</code> class.
 */
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

    /**
     * Verifies that it finds a collision for a simple AND expression.
     */
    @Test
    public void shouldReturnCollisionForAND() {
        String[] content = new String[] {"o1 = i1 ∧ i2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        Attack attack = new Attack(booleanFunction);
        assertTrue(attack.perform() instanceof CollisionFound);
    }

    /**
     * Verifies that it returns that no collision could be found for a double
     * identity function.
     */
    @Test
    public void shouldReturnNoCollisionFoundForIdentityWithTwoParameters() {
        String[] content = new String[] {"o1 = i1", "o2 = i2"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        Attack attack = new Attack(booleanFunction);
        assertTrue(attack.perform() instanceof NoCollisionFound);
    }

    /**
     * Verifies that it returns after a limited number of iterations.
     */
    @Test
    public void shouldReturnNoCollisionFoundYetForIdentityWithSixParameters() {
        String[] content = new String[] {"o1 = i1", "o2 = i2", "o3 = i3", "o4 = i4", "o5 = i5", "o6 = i6"};
        BooleanFunction booleanFunction = BooleanFunction.parse(content);
        Attack attack = new Attack(booleanFunction);
        assertTrue(attack.perform() instanceof NoCollisionFoundYet);
    }
}
