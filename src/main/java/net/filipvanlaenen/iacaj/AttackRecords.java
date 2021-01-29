package net.filipvanlaenen.iacaj;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AttackRecords {
    private final List<HashSet<BooleanFunction>> booleanFunctions = new ArrayList<HashSet<BooleanFunction>>();

    public AttackRecords(BooleanFunction booleanFunction) {
        for (int i = 0; i < booleanFunction.getNumberOfInputParameters(); i++) {
            booleanFunctions.add(i, new HashSet<BooleanFunction>());
        }
        add(booleanFunction);
    }

    public void add(BooleanFunction booleanFunction) {
        booleanFunctions.get(booleanFunction.getNumberOfConstraints()).add(booleanFunction);
    }

    private BooleanFunction findNextCollisionCandidate(BooleanFunction parent) {
        BooleanFunction result = new BooleanFunction(parent);
        InputParameter inputParameter = parent.getInputParameters().iterator().next();
        result.addExpression(BooleanExpression.parse(inputParameter.getName() + " = False"));
        return result;
    }

    private BooleanFunction findNextExtensionSource(int numberOfConstraints) {
        return booleanFunctions.get(numberOfConstraints).iterator().next();
    }

    int findNextLineToAttack() {
        for (int i = 0; i < booleanFunctions.size(); i++) {
            if (booleanFunctions.get(i).size() == 0) {
                return i;
            }
        }
        return 1;
    }

    BooleanFunction findNextCollisionCandidate() {
        /*
         * List<AttackLine> sortedLines = sortAttackLines();
         * 
         * for (AttackLine line : sortedLines) {
         * 
         * · candidate = line.findNextCollisionCandidate();
         * 
         * · if (candidate != null) {
         * 
         * · · return candidate;
         * 
         * · }
         * 
         * }
         * 
         * return null;
         */
        int numberOfConstraints = findNextLineToAttack();
        BooleanFunction extensionSource = findNextExtensionSource(numberOfConstraints - 1);
        return findNextCollisionCandidate(extensionSource);
    }
}
