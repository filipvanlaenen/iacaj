package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>BooleanExpression</code> class.
 */
public class BooleanExpressionTest {
    /**
     * Verifies that parsing an empty line returns null.
     */
    @Test
    public void parsingAnEmptyLineShouldReturnNull() {
        assertNull(BooleanExpression.parse(""));
    }

    /**
     * Verifies that parsing a line starting with # returns null.
     */
    @Test
    public void parsingACommentShouldReturnNull() {
        assertNull(BooleanExpression.parse("# A comment"));
    }

    /**
     * Verifies that parsing detects AND as the operator.
     */
    @Test
    public void parsingDetectsAndOperator() {
        BooleanExpression expression = BooleanExpression.parse("o1 = i1 ∧ i2");
        assertEquals(BooleanExpression.Operator.And, expression.getOperator());
    }

    /**
     * Verifies that parsing detects OR as the operator.
     */
    @Test
    public void parsingDetectsOrOperator() {
        BooleanExpression expression = BooleanExpression.parse("o1 = i1 ∨ i2");
        assertEquals(BooleanExpression.Operator.Or, expression.getOperator());
    }

    /**
     * Verifies that parsing detects XOR as the operator.
     */
    @Test
    public void parsingDetectsXorOperator() {
        BooleanExpression expression = BooleanExpression.parse("o1 = i1 ⊻ i2");
        assertEquals(BooleanExpression.Operator.Xor, expression.getOperator());
    }
}
