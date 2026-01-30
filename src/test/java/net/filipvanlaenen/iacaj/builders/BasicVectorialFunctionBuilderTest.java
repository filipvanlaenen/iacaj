package net.filipvanlaenen.iacaj.builders;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.iacaj.expressions.Operator;

public class BasicVectorialFunctionBuilderTest {
    @Test
    public void foo() {
        BasicVectorialFunctionBuilder builder = new BasicVectorialFunctionBuilder();
        builder.operator(Operator.AND);
        builder.outputVectorWidth(4);
        builder.build();
    }
}
