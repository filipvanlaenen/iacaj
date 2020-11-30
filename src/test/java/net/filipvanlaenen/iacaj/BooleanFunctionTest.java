package net.filipvanlaenen.iacaj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.iacaj.BooleanFunction;

/**
 * Unit tests on the <code>BooleanFunction</code> class.
 */
public class BooleanFunctionTest {
	@Test
	public void shouldParseSimpleAndExpressionWithOneOutputParameter() {
		String[] content = new String[] { "o1 = i1 ∧ i2" };
		BooleanFunction booleanFunction = BooleanFunction.parse(content);
		assertEquals(1, booleanFunction.getNumberOfOutputParameters());
	}

	@Test
	public void shouldParseSimpleAndExpressionWithTwoInputParameters() {
		String[] content = new String[] { "o1 = i1 ∧ i2" };
		BooleanFunction booleanFunction = BooleanFunction.parse(content);
		assertEquals(2, booleanFunction.getNumberOfInputParameters());
	}

	@Test
	public void shouldParseSimpleAndExpressionWithThreeInputParameters() {
		String[] content = new String[] { "o1 = i1 ∧ i2 ∧ i3" };
		BooleanFunction booleanFunction = BooleanFunction.parse(content);
		assertEquals(3, booleanFunction.getNumberOfInputParameters());
	}
}
