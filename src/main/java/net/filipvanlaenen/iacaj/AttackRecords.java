package net.filipvanlaenen.iacaj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class keeping track of all performed attacks on a Boolean function.
 */
public class AttackRecords {
    public class AttackRecord {
        private final Set<InputParameter> inputParameters;
        private final BooleanConstraints constraints;

        public AttackRecord(Set<InputParameter> inputParameters, Set<BooleanConstraint> constraints) {
            this.inputParameters = inputParameters;
            this.constraints = new BooleanConstraints(constraints);
        }

        public Set<InputParameter> getInputParameters() {
            return inputParameters;
        }

        public BooleanConstraints getConstraints() {
            return constraints;
        }
    }

    public class AttackLine {
        private final Map<BooleanConstraints, AttackRecord> attacks = new HashMap<BooleanConstraints, AttackRecord>();
        private final AttackLine predecessor;

        public AttackLine() {
            this.predecessor = null;
        }

        public AttackLine(AttackLine predecessor) {
            this.predecessor = predecessor;
        }

        public BooleanConstraints findNextCollisionCandidate() {
            List<AttackRecord> parents = predecessor.getSortedExtensionPoints();
            for (AttackRecord parent : parents) {
                BooleanConstraints candidate = extendParent(parent);
                if (candidate != null) {
                    return candidate;
                }
            }
            return null;
        }

        private BooleanConstraints extendParent(AttackRecord parent) {
            List<InputParameter> inputParameters = new ArrayList<InputParameter>(parent.getInputParameters());
            // TODO sort
            BooleanConstraints parentConstraints = parent.getConstraints();
            for (InputParameter inputParameter : inputParameters) {
                BooleanConstraints extensionWithFalse = parentConstraints.extend(inputParameter, "False");
                if (!attacks.containsKey(extensionWithFalse)) {
                    return createExtension(parentConstraints, inputParameter, "False");
                }
                BooleanConstraints extensionWithTrue = parentConstraints.extend(inputParameter, "True");
                if (!attacks.containsKey(extensionWithTrue)) {
                    return createExtension(parentConstraints, inputParameter, "True");
                }
            }
            return null;
        }

        private BooleanConstraints createExtension(BooleanConstraints parent, InputParameter inputParameter,
                String string) {
            return parent.extend(inputParameter, string);
        }

        private List<AttackRecord> getSortedExtensionPoints() {
            List<AttackRecord> extensionPoints = new ArrayList<AttackRecord>(attacks.values());
            // TODO sort
            return extensionPoints;
        }

        public void add(BooleanFunction booleanFunction) {
            BooleanConstraints constraints = new BooleanConstraints(booleanFunction.getConstraints());
            AttackRecord record = new AttackRecord(booleanFunction.getInputParameters(),
                    booleanFunction.getConstraints());
            attacks.put(constraints, record);
        }

        public int size() {
            return attacks.size();
        }
    }

    /**
     * The attack lines in which the resolved Boolean functions are stored by number
     * of constraints.
     */
    private final AttackLine[] attackLines;

    /**
     * Constructor taking a Boolean function as its parameter. It is assumed that
     * the Boolean function has no constraints.
     *
     * @param booleanFunction The Boolean function to attack.
     */
    public AttackRecords(final BooleanFunction booleanFunction) {
        attackLines = new AttackLine[booleanFunction.getNumberOfInputParameters()];
        attackLines[0] = new AttackLine();
        for (int i = 1; i < attackLines.length; i++) {
            attackLines[i] = new AttackLine(attackLines[i - 1]);
        }
        add(booleanFunction);
    }

    /**
     * Adds a Boolean function to the attack records. It is assumed that the Boolean
     * function is already resolved.
     *
     * @param booleanFunction The Boolean function to be added to the attack
     *                        records.
     */
    public void add(final BooleanFunction booleanFunction) {
        attackLines[booleanFunction.getNumberOfConstraints()].add(booleanFunction);
    }

    BooleanConstraints findNextCollisionCandidate() {
        List<AttackLine> sortedLines = sortAttackLines();
        for (AttackLine line : sortedLines) {
            BooleanConstraints candidate = line.findNextCollisionCandidate();
            if (candidate != null) {
                return candidate;
            }
        }
        return null;
    }

    /**
     * Calculates the number of attacks stored.
     *
     * @return The number of attacks stored.
     */
    int size() {
        int result = 0;
        for (AttackLine attackLine : attackLines) {
            result += attackLine.size();
        }
        return result - 1;
    }

    private List<AttackLine> sortAttackLines() {
        List<AttackLine> sortedLines = new ArrayList<AttackLine>(Arrays.asList(attackLines));
        sortedLines.remove(attackLines[0]);
        // TODO Sort
        return sortedLines;
    }
}
