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
        BooleanOperation operation = (BooleanOperation) BooleanExpression.parse("o1 = i1 ∧ i2");
        assertEquals(BooleanOperation.Operator.And, operation.getOperator());
    }

    /**
     * Verifies that parsing detects OR as the operator.
     */
    @Test
    public void parsingDetectsOrOperator() {
        BooleanOperation operation = (BooleanOperation) BooleanExpression.parse("o1 = i1 ∨ i2");
        assertEquals(BooleanOperation.Operator.Or, operation.getOperator());
    }

    /**
     * Verifies that parsing detects XOR as the operator.
     */
    @Test
    public void parsingDetectsXorOperator() {
        BooleanOperation operation = (BooleanOperation) BooleanExpression.parse("o1 = i1 ⊻ i2");
        assertEquals(BooleanOperation.Operator.Xor, operation.getOperator());
    }
}
