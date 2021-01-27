package net.filipvanlaenen.iacaj;

import java.util.Set;

public class Attack {
    private final BooleanFunction booleanFunction;

    public Attack(BooleanFunction booleanFunction) {
        this.booleanFunction = booleanFunction;
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
        AttackRecords records = new AttackRecords(booleanFunction);
        boolean collisionFound = false;
        BooleanFunction collisionCandidate = booleanFunction;
        while (!collisionFound) {
            int numberOfConstraints = records.findNextLineToAttack();
            BooleanFunction extensionSource = records.findNextExtensionSource(numberOfConstraints - 1);
            collisionCandidate = records.findNextCollisionCandidate(extensionSource);
            collisionCandidate.resolve();
            records.add(collisionCandidate);
            collisionFound = numberOfConstraints
                    + collisionCandidate.getNumberOfInputParameters() < numberOfInputParameters;
        }
        return new CollisionFound(collisionCandidate, inputParameters);
    }
}
