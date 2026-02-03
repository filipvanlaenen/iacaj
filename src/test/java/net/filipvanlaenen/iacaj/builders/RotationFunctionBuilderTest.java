package net.filipvanlaenen.iacaj.builders;

import org.junit.jupiter.api.Test;

public class RotationFunctionBuilderTest {
    @Test
    public void foo() {
        RotationFunctionBuilder builder = new RotationFunctionBuilder();
        builder.outputVectorWidth(4);
        builder.rotateRight(2);
        builder.build();
    }
}
