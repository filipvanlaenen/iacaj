package net.filipvanlaenen.iacaj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AttackRecords {
    public class AttackLine {
        private final Set<BooleanFunction> booleanFunctions = new HashSet<BooleanFunction>();
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
            List<InputParameter> inputParameters =new ArrayList<InputParameter> (parent.getInputParameters());
            // TODO sort
            // TODO loop
            BooleanFunction result = new BooleanFunction(parent);
            InputParameter inputParameter = inputParameters.iterator().next();
            result.addExpression(BooleanExpression.parse(inputParameter.getName() + " = False"));
            return result;
        }

        private List<BooleanFunction> getSortedExtensionPoints() {
            List<BooleanFunction> extensionPoints = new ArrayList<BooleanFunction>(booleanFunctions);
            // TODO sort
            return extensionPoints;
        }

        public void add(BooleanFunction booleanFunction) {
            booleanFunctions.add(booleanFunction);
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

    private List<AttackLine> sortAttackLines() {
        List<AttackLine> sortedLines = new ArrayList<AttackLine>(Arrays.asList(attackLines));
        sortedLines.remove(attackLines[0]);
        // TODO Sort
        return sortedLines;
    }
}
