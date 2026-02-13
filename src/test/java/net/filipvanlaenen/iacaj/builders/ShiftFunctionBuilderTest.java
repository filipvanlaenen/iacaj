package net.filipvanlaenen.iacaj.builders;

import org.junit.jupiter.api.Test;

public class ShiftFunctionBuilderTest {
    @Test
    public void foo() {
        ShiftFunctionBuilder builder = new ShiftFunctionBuilder();
        builder.outputVectorWidth(4);
        builder.shiftRight(2);
        builder.build().toString();
    }
}
