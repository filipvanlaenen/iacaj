package net.filipvanlaenen.iacaj;

import java.util.HashSet;
import java.util.Set;

public class AttackOverview {
    private Set<BooleanFunction> booleanFunctions = new HashSet<BooleanFunction>();

    public int findNextRowToAttack() {
        return 1;
    }

    public BooleanFunction findNextExtensionSource(int numberOfConstraints) {
        return booleanFunctions.iterator().next();
    }

    public BooleanFunction findNextCollisionCandidate(BooleanFunction parent) {
        BooleanFunction result = new BooleanFunction(parent);
        InputParameter inputParameter = parent.getInputParameters().iterator().next();
        result.addExpression(BooleanExpression.parse(inputParameter.getName() + " = True"));
        return result;
    }

    public void add(BooleanFunction booleanFunction) {
        booleanFunctions.add(booleanFunction);
    }
}
