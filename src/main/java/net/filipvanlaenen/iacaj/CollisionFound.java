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
        sb.append(String.join("\n    ",
                constraints.stream().map(BooleanConstraint::toString).collect(Collectors.toList())));
        sb.append("\n");
        sb.append("  Eliminated input bit:\n");
        sb.append("  Free input bits: ");
        Set<InputParameter> freeInputParameters = collision.getInputParameters();
        sb.append(String.join(", ",
                freeInputParameters.stream().map(InputParameter::getName).collect(Collectors.toList())));
        return sb.toString();
    }
}
