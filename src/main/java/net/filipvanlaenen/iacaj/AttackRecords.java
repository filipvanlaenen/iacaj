package net.filipvanlaenen.iacaj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class keeping track of all performed attacks on a Boolean function.
 */
public class AttackRecords {
    public class AttackLine {
        private final Map<BooleanConstraints, BooleanFunction> booleanFunctions = new HashMap<BooleanConstraints, BooleanFunction>();
        private final AttackLine predecessor;

        public AttackLine() {
            this.predecessor = null;
        }

        public AttackLine(AttackLine predecessor) {
            this.predecessor = predecessor;
        }

        public BooleanFunction findNextCollisionCandidate() {
            List<BooleanFunction> parents = predecessor.getSortedExtensionPoints();
            for (BooleanFunction parent : parents) {
                BooleanFunction candidate = extendParent(parent);
                if (candidate != null) {
                    return candidate;
                }
            }
            return null;
        }

        private BooleanFunction extendParent(BooleanFunction parent) {
            List<InputParameter> inputParameters = new ArrayList<InputParameter>(parent.getInputParameters());
            // TODO sort
            BooleanConstraints parentConstraints = new BooleanConstraints(parent.getConstraints());
            for (InputParameter inputParameter : inputParameters) {
                BooleanConstraints extensionWithFalse = parentConstraints.extend(inputParameter, "False");
                if (!booleanFunctions.containsKey(extensionWithFalse)) {
                    return createExtension(parent, inputParameter, "False");
                }
                BooleanConstraints extensionWithTrue = parentConstraints.extend(inputParameter, "True");
                if (!booleanFunctions.containsKey(extensionWithTrue)) {
                    return createExtension(parent, inputParameter, "True");
                }
            }
            return null;
        }

        private BooleanFunction createExtension(BooleanFunction parent, InputParameter inputParameter, String string) {
            BooleanFunction result = new BooleanFunction(parent);
            result.addExpression(BooleanExpression.parse(inputParameter.getName() + " = " + string));
            return result;
        }

        private List<BooleanFunction> getSortedExtensionPoints() {
            List<BooleanFunction> extensionPoints = new ArrayList<BooleanFunction>(booleanFunctions.values());
            // TODO sort
            return extensionPoints;
        }

        public void add(BooleanFunction booleanFunction) {
            booleanFunctions.put(new BooleanConstraints(booleanFunction.getConstraints()), booleanFunction);
        }

        public int size() {
            return booleanFunctions.size();
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

    BooleanFunction findNextCollisionCandidate() {
        List<AttackLine> sortedLines = sortAttackLines();
        for (AttackLine line : sortedLines) {
            BooleanFunction candidate = line.findNextCollisionCandidate();
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
