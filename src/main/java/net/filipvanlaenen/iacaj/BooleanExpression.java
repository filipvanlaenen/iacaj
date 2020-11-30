package net.filipvanlaenen.iacaj;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BooleanExpression {

	private Set<InputParameter> inputParameters = new HashSet<InputParameter>();

	public BooleanExpression(String resultName, String[] operandNames) {
		for (String operandName : operandNames) {
			// TODO: Differentiate between input parameters and internal variables
			// TODO: Negations
			// TODO: Look-up based on operand name
			inputParameters.add(new InputParameter()); 
		}
	}

	public static BooleanExpression parse(String line) {
		// TODO: Ignore comments starting with #
		// TODO: Ignore empty lines
		String[] elements = line.split("=");
		String resultName = elements[0].trim();
		String operation = elements[1].trim();
		// TODO: Operator OR
		// TODO: Operator XOR
		String[] operandNames = Arrays.stream(operation.split("∧")).map(String::trim).toArray(String[]::new);
		return new BooleanExpression(resultName, operandNames);
	}

	public Set<InputParameter> getInputParameters() {
		return inputParameters;
	}
}
