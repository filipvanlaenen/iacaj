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
        if (inputParameters.size() < initialInputParameters.size()) {
            return new SomeInputParametersEliminated();
        }
        return null;
    }
}
