package net.filipvanlaenen.iacaj;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CollisionFound implements AttackResult {
	private final BooleanFunction collision;
	private final Set<InputParameter> inputParameters;

	public CollisionFound(BooleanFunction collission, Set<InputParameter> inputParameters) {
		this.collision = collission;
		this.inputParameters = inputParameters;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Collision found:\n");
		List<BooleanConstraint> constraints = collision.getSortedConstraints();
		if (constraints.size() == 1) {
			sb.append("  Constraint:\n");
		} else {
			sb.append("  Constraints:\n");
		}
		sb.append("    ");
		List<String> constraintLines = constraints.stream().map(BooleanConstraint::toString)
				.collect(Collectors.toList());
		sb.append(String.join("\n    ", constraintLines));
		sb.append("\n");
		Set<String> eliminatedInputParameterNames = inputParameters.stream().map(InputParameter::getName)
				.collect(Collectors.toSet());
		Set<String> constrainedInputParameterNames = constraints.stream().map(BooleanConstraint::getName)
				.collect(Collectors.toSet());
		eliminatedInputParameterNames.removeAll(constrainedInputParameterNames);
		Set<String> freeInputParameterNames = collision.getInputParameters().stream().map(InputParameter::getName)
				.collect(Collectors.toSet());
		eliminatedInputParameterNames.removeAll(freeInputParameterNames);
		if (eliminatedInputParameterNames.size() == 1) {
			sb.append("  Eliminated input parameter: ");
		} else {
			sb.append("  Eliminated input parameters: ");
		}
		sb.append(String.join(", ", eliminatedInputParameterNames));
		sb.append("\n");
		if (freeInputParameterNames.size() == 1) {
			sb.append("  Free input parameter: ");
		} else {
			sb.append("  Free input parameters: ");
		}
		if (freeInputParameterNames.isEmpty()) {
			sb.append("none");
		} else {
			sb.append(String.join(", ", freeInputParameterNames));
		}
		return sb.toString();
	}
}
