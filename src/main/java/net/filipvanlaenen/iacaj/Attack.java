package net.filipvanlaenen.iacaj;

import java.util.Set;

public class Attack {
    private static final int MAXIMUM_NUMBER_OF_ITERATIONS = 128;
    private final BooleanFunction booleanFunction;
    private AttackRecords records;
    private int numberOfInputParameters;
    private Set<InputParameter> inputParameters;

    public Attack(BooleanFunction booleanFunction) {
        this.booleanFunction = booleanFunction;
    }

    private AttackResult initialize() {
        Set<InputParameter> initialInputParameters = booleanFunction.getInputParameters();
        if (initialInputParameters.isEmpty()) {
            return new NoInputParameters();
        }
        booleanFunction.resolve();
        inputParameters = booleanFunction.getInputParameters();
        if (inputParameters.isEmpty()) {
            return new AllInputParametersEliminated();
        }
        numberOfInputParameters = inputParameters.size();
        if (numberOfInputParameters < initialInputParameters.size()) {
            return new SomeInputParametersEliminated();
        }
        records = new AttackRecords(booleanFunction);
        return null;
    }

    public AttackResult perform() {
        if (records == null) {
            AttackResult result = initialize();
            if (result != null) {
                return result;
            }
        }
        boolean collisionFound = false;
        BooleanFunction collisionCandidate = booleanFunction;
        for (int i = 0; i < MAXIMUM_NUMBER_OF_ITERATIONS && !collisionFound; i++) {
            BooleanConstraints constraints = records.findNextCollisionCandidate();
            if (constraints == null) {
                return new NoCollisionFound();
            }
            collisionCandidate = new BooleanFunction(booleanFunction, constraints);
            collisionCandidate.resolve();
            records.add(collisionCandidate);
            collisionFound = collisionCandidate.getNumberOfConstraints()
                    + collisionCandidate.getNumberOfInputParameters() < numberOfInputParameters;
        }
        if (collisionFound) {
            return new CollisionFound(collisionCandidate, inputParameters);
        } else {
            return new NoCollisionFoundYet(records.size());
        }
    }
}
