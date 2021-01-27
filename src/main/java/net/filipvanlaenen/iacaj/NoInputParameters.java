package net.filipvanlaenen.iacaj;

/**
 * Attack result when no input parameters were detected in the Boolean function.
 */
public final class NoInputParameters implements AttackResult {
    @Override
    public String toString() {
        return "No input parameters detected in the Boolean function.";
    }
}
