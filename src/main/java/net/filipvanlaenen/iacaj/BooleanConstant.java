package net.filipvanlaenen.iacaj;

import net.filipvanlaenen.kolektoj.OrderedCollection;

/**
 * Class representing a right hand side of a Boolean operation with a constant, i.e. either True or False.
 */
@Deprecated
public final class BooleanConstant extends BooleanRightHandSide {
    /**
     * The constant value.
     */
    private final boolean value;
    /**
     * The Boolean constant representing false.
     */
    public static final BooleanConstant FALSE = new BooleanConstant(false);
    /**
     * The Boolean constant representing true.
     */
    public static final BooleanConstant TRUE = new BooleanConstant(true);

    /**
     * Returns the Boolean constant for the provided Boolean value.
     *
     * @param value The value for which to return the Boolean constant.
     * @return The Boolean constant representing the provided Boolean value.
     */
    static BooleanConstant get(final boolean value) {
        return value ? TRUE : FALSE;
    }

    /**
     * Constructor taking the constant value as its parameter.
     *
     * @param value The constant value.
     */
    private BooleanConstant(final boolean value) {
        this.value = value;
    }

    @Override
    protected BooleanConstant deepClone() {
        // Returns the same object, contrary to the definition of deep clone, because in
        // reality there are only two different objects for this class.
        return this;
    }

    @Override
    public OrderedCollection<InputParameter> getInputParameters() {
        return OrderedCollection.<InputParameter>empty();
    }

    @Override
    public OrderedCollection<InternalVariable> getInternalVariables() {
        return OrderedCollection.<InternalVariable>empty();
    }

    @Override
    protected BooleanOperator getOperator() {
        return null;
    }

    @Override
    protected boolean isFalse() {
        return !value;
    }

    @Override
    protected boolean isTrue() {
        return value;
    }

    @Override
    protected BooleanRightHandSide resolve(final BooleanFunction booleanFunction) {
        return this;
    }

    @Override
    public String toJavaString() {
        return value ? "true" : "false";
    }

    @Override
    public String toString() {
        return value ? "True" : "False";
    }
}
