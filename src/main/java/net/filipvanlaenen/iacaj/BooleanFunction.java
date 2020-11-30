package net.filipvanlaenen.iacaj;

import java.util.HashSet;
import java.util.Set;

/**
 * Class representing a (multi-dimensional) Boolean function.
 */
public class BooleanFunction {
	private Set<BooleanExpression> expressions = new HashSet<BooleanExpression>();
	private Set<InputParameter> inputParameters = new HashSet<InputParameter>();

	/**
	 * Factory method creating a BooleanFunction instance based on an array of
	 * strings.
	 *
	 * @param content An array of strings representing a Boolean function.
	 * @return A Boolean function.
	 */
	public static BooleanFunction parse(String[] content) {
		BooleanFunction result = new BooleanFunction();
		for (String line : content) {
			result.addExpression(BooleanExpression.parse(line));
		}
		return result;
	}

	private void addExpression(BooleanExpression booleanExpression) {
		expressions.add(booleanExpression);
		inputParameters.addAll(booleanExpression.getInputParameters());
	}

	/**
	 * Returns the number of input parameters.
	 *
	 * @return The number of input parameters.
	 */
	public int getNumberOfInputParameters() {
		return inputParameters.size();
	}

	/**
	 * Returns the number of output parameters.
	 *
	 * @return The number of output parameters.
	 */
	public int getNumberOfOutputParameters() {
		return 1; // TODO
	}
}
