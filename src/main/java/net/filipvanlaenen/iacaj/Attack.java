package net.filipvanlaenen.iacaj;

import java.util.Set;

/**
 * Class performing the attack on a Boolean function.
 */
public class Attack {
    /**
     * The maximal number of iterations before the <code>perform</code> method will
     * return and report progress.
     */
    static final int MAXIMUM_NUMBER_OF_ITERATIONS = 128;

    /**
     * The Boolean function under attack.
     */
    private final BooleanFunction booleanFunction;
    /**
     * The records for the attack.
     */
    private AttackRecords records;
    /**
     * The number of input parameters of the Boolean function.
     */
    private int numberOfInputParameters;
    /**
     * The input parameters of the Boolean function.
     */
    private Set<InputParameter> inputParameters;

    /**
     * Constructor taking the Boolean function to attack as its parameter.
     *
     * @param booleanFunction The Boolean function to attack.
     */
    public Attack(final BooleanFunction booleanFunction) {
        this.booleanFunction = booleanFunction;
    }

    /**
     * Initialized the attack. This includes verifying that the Boolean function has
     * input parameters, and that none of the input parameters are eliminated after
     * resolving the Boolean function without constraints
     *
     * @return <code>null</code> if the attack can start, or a final attack result
     *         if the attack shouldn't start.
     */
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

    /**
     * Performs the attack. Can be called again to continue if a progress report was
     * returned by the previous call.
     *
     * @return A final attack result or a progress report.
     */
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
                    + collisionCandidate.getNumberOfInputParametersInCalculation() < numberOfInputParameters;
        }
        if (collisionFound) {
            return new CollisionFound(collisionCandidate, inputParameters);
        } else {
            return new NoCollisionFoundYet(records.size(), records.getBestBooleanConstraints(),
                    records.getBestNumberOfExpressions());
        }
    }
}
