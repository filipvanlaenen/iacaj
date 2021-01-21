package net.filipvanlaenen.iacaj;

import java.util.Set;

public class Attack {

    public class AttackRow {
        public void add(BooleanFunction booleanFunction) {
        }
    }

    private final BooleanFunction booleanFunction;

    public Attack(BooleanFunction booleanFunction) {
        this.booleanFunction = booleanFunction;
    }

    private BooleanFunction extendParent(BooleanFunction parent, AttackRow attackRow) {
        return null;
    }

    private BooleanFunction findNextParent(AttackRow attackRow) {
        return null;
    }

    private int findNextRowToAttack(AttackRow[] attackRows) {
        return 1;
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
        attackRows[0] = new AttackRow();
        attackRows[0].add(booleanFunction);
        boolean collissionFound = false;
        BooleanFunction collissionCandidate = booleanFunction;
        while (!collissionFound) {
            int numberOfConstraints = findNextRowToAttack(attackRows);
            BooleanFunction extensionPoint = findNextParent(attackRows[numberOfConstraints - 1]);
            collissionCandidate = extendParent(extensionPoint, attackRows[numberOfConstraints]);
            collissionCandidate.resolve();
            attackRows[numberOfConstraints].add(collissionCandidate);
            collissionFound = numberOfConstraints
                    + collissionCandidate.getInputParameters().size() < numberOfInputParameters;
        }
        return new CollissionFound(collissionCandidate);
    }
}
