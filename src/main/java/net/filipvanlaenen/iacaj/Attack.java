package net.filipvanlaenen.iacaj;

import java.util.HashSet;
import java.util.Set;

public class Attack {
    public class AttackRow {
        private Set<BooleanFunction> booleanFunctions = new HashSet<BooleanFunction>();

        public void add(BooleanFunction booleanFunction) {
            booleanFunctions.add(booleanFunction);
        }

        public BooleanFunction findNextExtensionSource() {
            return booleanFunctions.iterator().next(); // TODO
        }
    }

    private final BooleanFunction booleanFunction;

    public Attack(BooleanFunction booleanFunction) {
        this.booleanFunction = booleanFunction;
    }

    private BooleanFunction findNextCollissionCandidate(BooleanFunction parent, AttackRow attackRow) {
        BooleanFunction result = new BooleanFunction(parent);
        InputParameter inputParameter = parent.getInputParameters().iterator().next(); // TODO
        result.addExpression(BooleanExpression.parse(inputParameter.getName() + " = False")); // TODO
        return result;
    }

    private int findNextRowToAttack(AttackRow[] attackRows) {
        return 1; // TODO
    }

    public AttackResult perform() {
        Set<InputParameter> initialInputParameters = booleanFunction.getInputParameters();
        if (initialInputParameters.isEmpty()) {
            return new NoInputParameters();
        }
        booleanFunction.resolve();
        Set<InputParameter> inputParameters = booleanFunction.getInputParameters();
        if (inputParameters.isEmpty()) {
            return new AllInputParametersEliminated();
        }
        int numberOfInputParameters = inputParameters.size();
        if (numberOfInputParameters < initialInputParameters.size()) {
            return new SomeInputParametersEliminated();
        }
        AttackRow[] attackRows = new AttackRow[numberOfInputParameters];
        for (int i = 0; i < numberOfInputParameters; i++) {
            attackRows[i] = new AttackRow();
        }
        attackRows[0].add(booleanFunction);
        boolean collissionFound = false;
        BooleanFunction collissionCandidate = booleanFunction;
        while (!collissionFound) { // TODO: Avoid infinite loops
            int numberOfConstraints = findNextRowToAttack(attackRows);
            BooleanFunction extensionSource = attackRows[numberOfConstraints - 1].findNextExtensionSource();
            collissionCandidate = findNextCollissionCandidate(extensionSource, attackRows[numberOfConstraints]);
            collissionCandidate.resolve();
            attackRows[numberOfConstraints].add(collissionCandidate);
            collissionFound = numberOfConstraints
                    + collissionCandidate.getNumberOfInputParameters() < numberOfInputParameters;
        }
        return new CollissionFound(collissionCandidate);
    }
}
