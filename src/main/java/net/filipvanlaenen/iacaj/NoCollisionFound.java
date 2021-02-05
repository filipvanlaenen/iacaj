package net.filipvanlaenen.iacaj;

/**
 * Attack result when no collision could be found in the Boolean function.
 */
public final class NoCollisionFound implements AttackResult {
    @Override
    public String toString() {
        return "No collision found in the Boolean function.";
    }
}
