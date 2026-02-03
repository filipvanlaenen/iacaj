package net.filipvanlaenen.iacaj.builders;

import org.junit.jupiter.api.Test;

public class RotationFunctionBuilderTest {
    @Test
    public void foo() {
        ShiftFunctionBuilder builder = new ShiftFunctionBuilder();
        builder.outputVectorWidth(4);
        builder.shiftRight(2);
        builder.build();
    }
}
