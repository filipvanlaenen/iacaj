package net.filipvanlaenen.iacaj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AttackRecords {
    public class BooleanConstraints {
        private Set<BooleanConstraint> constraints;

        public BooleanConstraints(Set<BooleanConstraint> constraints) {
            this.constraints = new HashSet<BooleanConstraint>(constraints);
        }

        public int size() {
            return constraints.size();
        }

        public BooleanConstraints extend(InputParameter inputParameter, String string) {
            BooleanConstraints result = new BooleanConstraints(constraints);
            result.add((BooleanConstraint) BooleanConstraint.parse(inputParameter.getName(), string));
            return result;
        }

        private void add(BooleanConstraint parse) {
            constraints.add(parse);
        }

        @Override
        public boolean equals(Object object) {
            if (object instanceof BooleanConstraints) {
                BooleanConstraints other = (BooleanConstraints) object;
                if (constraints.size() != other.constraints.size()) {
                    return false;
                }
                for (BooleanConstraint constraint : constraints) {
                    if (!other.constraints.contains(constraint)) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            int result = 0;
            for (BooleanConstraint constraint : constraints) {
                result += constraint.hashCode();
            }
            return result;
        }

        @Override
        public String toString() {
            String result = "<";
            for (BooleanConstraint constraint : constraints) {
                result += constraint.toString();
            }
            return result + ">";
        }
    }

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

    private final AttackLine[] attackLines;

    public AttackRecords(BooleanFunction booleanFunction) {
        attackLines = new AttackLine[booleanFunction.getNumberOfInputParameters()];
        attackLines[0] = new AttackLine();
        for (int i = 1; i < attackLines.length; i++) {
            attackLines[i] = new AttackLine(attackLines[i - 1]);
        }
        add(booleanFunction);
    }

    public void add(BooleanFunction booleanFunction) {
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

    int size() {
        int result = 0;
        for (AttackLine attackLine : attackLines) {
            result += attackLine.size();
        }
        return result;
    }

    private List<AttackLine> sortAttackLines() {
        List<AttackLine> sortedLines = new ArrayList<AttackLine>(Arrays.asList(attackLines));
        sortedLines.remove(attackLines[0]);
        // TODO Sort
        return sortedLines;
    }
}
