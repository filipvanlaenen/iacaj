package net.filipvanlaenen.iacaj.expressions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import net.filipvanlaenen.kolektoj.ValueCollection;

/**
 * Utility class providing assertions and other useful functions for the unit tests.
 */
public final class Assertions {
    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Assertions() {
    }

    /**
     * Creates a value collection of variables from a list of variable names.
     *
     * @param names The names of the variables.
     * @return A value collection containing variables with the provided names.
     */
    static ValueCollection<Variable> createVariableCollection(final String... names) {
        // TODO: Refactor after the implementation of https://github.com/filipvanlaenen/kolektoj/issues/111
        Variable[] variables = new Variable[names.length];
        for (int i = 0; i < names.length; i++) {
            variables[i] = new Variable(names[i]);
        }
        return ValueCollection.of(variables);
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
