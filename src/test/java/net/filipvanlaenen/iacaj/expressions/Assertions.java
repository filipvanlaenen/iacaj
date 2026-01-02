package net.filipvanlaenen.iacaj.expressions;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Utility class providing assertions for the unit tests.
 */
public final class Assertions {
    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Assertions() {

    }

    /**
     * Verifies that simplifying the expression of the original string results into the expression of the expected
     * string.
     *
     * @param original The original string to be simplified.
     * @param expected The expected string.
     */
    static void parseSimplifyAndAssertEquals(final String original, final String expected) {
        assertEquals(Parser.parse(expected), Parser.parse(original).simplify());
    }
}
