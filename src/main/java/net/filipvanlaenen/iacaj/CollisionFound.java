package net.filipvanlaenen.iacaj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Attack result when a collision has been found.
 */
public final class CollisionFound implements AttackResult {
    /**
     * The Boolean function representing a collision.
     */
    private final BooleanFunction collision;
    /**
     * The original list of input parameters.
     */
    private final Set<InputParameter> inputParameters;

    /**
     * Constructor taking the boolean function representing the collision and the
     * original list of input parameters as parameter.
     *
     * @param collission      The Boolean function representing the collision.
     * @param inputParameters The original list of input parameters.
     */
    public CollisionFound(final BooleanFunction collission, final Set<InputParameter> inputParameters) {
        this.collision = collission;
        this.inputParameters = inputParameters;
    }

    /**
     * Converts a set of input parameters into a sorted comma-separated string with
     * the names.
     *
     * @param inputParameterSet The set with the input parameters.
     * @return A string with a comma-separated and sorted list of input parameters
     *         names.
     */
    private String asSortedInputParameterNamesString(final Set<InputParameter> inputParameterSet) {
        List<InputParameter> sortedInputParameters = new ArrayList<InputParameter>(inputParameterSet);
        Collections.sort(sortedInputParameters, new Comparator<InputParameter>() {
            @Override
            public int compare(final InputParameter inputParameter0, final InputParameter inputParameter1) {
                return inputParameter0.getNumber() - inputParameter1.getNumber();
            }
        });
        List<String> names = sortedInputParameters.stream().map(InputParameter::getName).collect(Collectors.toList());
        return String.join(", ", names);
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
        Set<InputParameter> eliminatedInputParameters = new HashSet<InputParameter>(inputParameters);
        Set<InputParameter> constrainedInputParameterNames = constraints.stream()
                .map(c -> InputParameter.get(c.getName())).collect(Collectors.toSet());
        eliminatedInputParameters.removeAll(constrainedInputParameterNames);
        Set<InputParameter> freeInputParameters = collision.getInputParametersInCalculation();
        eliminatedInputParameters.removeAll(freeInputParameters);
        if (eliminatedInputParameters.size() == 1) {
            sb.append("  Eliminated input parameter: ");
        } else {
            sb.append("  Eliminated input parameters: ");
        }
        sb.append(asSortedInputParameterNamesString(eliminatedInputParameters));
        sb.append("\n");
        if (freeInputParameters.size() == 1) {
            sb.append("  Free input parameter: ");
        } else {
            sb.append("  Free input parameters: ");
        }
        if (freeInputParameters.isEmpty()) {
            sb.append("none");
        } else {
            sb.append(asSortedInputParameterNamesString(freeInputParameters));
        }
        return sb.toString();
    }
}
