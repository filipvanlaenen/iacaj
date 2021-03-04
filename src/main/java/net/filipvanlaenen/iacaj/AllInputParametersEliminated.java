package net.filipvanlaenen.iacaj;

/**
 * Attack result when all input parameters are eliminated after the initial
 * resolution of the Boolean function.
 */
public class AllInputParametersEliminated implements AttackResult {
    @Override
    public String toString() {
        return "All input parameters were eliminated while resolving the Boolean function.";
    }
}
