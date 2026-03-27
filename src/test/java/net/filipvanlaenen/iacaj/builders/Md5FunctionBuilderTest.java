package net.filipvanlaenen.iacaj.builders;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.iacaj.expressions.Expression;
import net.filipvanlaenen.iacaj.expressions.LiteralExpression;
import net.filipvanlaenen.iacaj.expressions.Variable;
import net.filipvanlaenen.iacaj.expressions.VectorialFunction;
import net.filipvanlaenen.iacaj.expressions.Word;
import net.filipvanlaenen.kolektoj.ModifiableMap;

public class Md5FunctionBuilderTest {
    /**
     * Unit test verifying that <code>MD5[1]("") = 0x77777777fdd2ed94878888887431eda8</code>.
     */
    @Test
    public void md5WithOneRoundEmptyTestVector() {
        Md5FunctionBuilder builder = new Md5FunctionBuilder();
        builder.inputVectorName("i");
        builder.outputVectorName("o");
        builder.setNumberOfRounds(1);
        VectorialFunction md5 = builder.build();
        Word inputVector = new Word("i", 512);
        ModifiableMap<Variable, Expression> message = ModifiableMap.empty();
        message.add(inputVector.getAt(0), LiteralExpression.TRUE);
        for (int i = 1; i < 512; i++) {
            message.add(inputVector.getAt(i), LiteralExpression.FALSE);
        }
        VectorialFunction vf = md5.extendWih(message);
        Word outputVector = builder.getOutputVector();

        VectorialFunction resultA = vf.simplify(outputVector.getSlice(0, 32));
        ModifiableMap<Variable, Expression> expected = ModifiableMap.empty();
        expected.addAll(VectorialFunctionBuilder.buildAssignmentFunctions(outputVector.getSlice(0, 32), 0x77777777L));
        VectorialFunction e = new VectorialFunction(expected);
        assertTrue(e.containsSame(resultA));

        VectorialFunction resultB = vf.simplify(outputVector.getSlice(32, 64));
        ModifiableMap<Variable, Expression> expectedB = ModifiableMap.empty();
        expectedB.addAll(VectorialFunctionBuilder.buildAssignmentFunctions(outputVector.getSlice(32, 64), 0xfdd2ed94l));
        VectorialFunction eb = new VectorialFunction(expectedB);
        assertTrue(eb.containsSame(resultB));

        VectorialFunction resultC = vf.simplify(outputVector.getSlice(64, 96));
        ModifiableMap<Variable, Expression> expectedC = ModifiableMap.empty();
        expectedC.addAll(VectorialFunctionBuilder.buildAssignmentFunctions(outputVector.getSlice(64, 96), 0x87888888L));
        VectorialFunction ec = new VectorialFunction(expectedC);
        assertTrue(ec.containsSame(resultC));

        VectorialFunction resultD = vf.simplify(outputVector.getSlice(96, 128));
        ModifiableMap<Variable, Expression> expectedD = ModifiableMap.empty();
        expectedD
                .addAll(VectorialFunctionBuilder.buildAssignmentFunctions(outputVector.getSlice(96, 128), 0x7431eda8L));
        VectorialFunction ed = new VectorialFunction(expectedD);
        assertTrue(ed.containsSame(resultD));
    }

    /**
     * Unit test verifying that <code>MD5[2]("") = 0xffffffff663e63e57204db3dffffffff</code>.
     */
    @Test
    public void md5WithTwoRoundEmptyTestVector() {
        Md5FunctionBuilder builder = new Md5FunctionBuilder();
        builder.inputVectorName("i");
        builder.outputVectorName("o");
        builder.setNumberOfRounds(2);
        VectorialFunction md5 = builder.build();
        Word inputVector = new Word("i", 512);
        ModifiableMap<Variable, Expression> message = ModifiableMap.empty();
        message.add(inputVector.getAt(0), LiteralExpression.TRUE);
        for (int i = 1; i < 512; i++) {
            message.add(inputVector.getAt(i), LiteralExpression.FALSE);
        }
        VectorialFunction vf = md5.extendWih(message);
        Word outputVector = builder.getOutputVector();

        VectorialFunction resultA = vf.simplify(outputVector.getSlice(0, 32));
        ModifiableMap<Variable, Expression> expected = ModifiableMap.empty();
        expected.addAll(VectorialFunctionBuilder.buildAssignmentFunctions(outputVector.getSlice(0, 32), 0xffffffffL));
        VectorialFunction e = new VectorialFunction(expected);
        assertTrue(e.containsSame(resultA));

        VectorialFunction resultB = vf.simplify(outputVector.getSlice(32, 64));
        ModifiableMap<Variable, Expression> expectedB = ModifiableMap.empty();
        expectedB.addAll(VectorialFunctionBuilder.buildAssignmentFunctions(outputVector.getSlice(32, 64), 0x663e63e5L));
        VectorialFunction eb = new VectorialFunction(expectedB);
        assertTrue(eb.containsSame(resultB));

        VectorialFunction resultC = vf.simplify(outputVector.getSlice(64, 96));
        ModifiableMap<Variable, Expression> expectedC = ModifiableMap.empty();
        expectedC.addAll(VectorialFunctionBuilder.buildAssignmentFunctions(outputVector.getSlice(64, 96), 0x7204db3dL));
        VectorialFunction ec = new VectorialFunction(expectedC);
        assertTrue(ec.containsSame(resultC));

        VectorialFunction resultD = vf.simplify(outputVector.getSlice(96, 128));
        ModifiableMap<Variable, Expression> expectedD = ModifiableMap.empty();
        expectedD
                .addAll(VectorialFunctionBuilder.buildAssignmentFunctions(outputVector.getSlice(96, 128), 0xffffffffL));
        VectorialFunction ed = new VectorialFunction(expectedD);
        assertTrue(ed.containsSame(resultD));
    }

    /**
     * Unit test verifying that <code>MD5("") = 0xd41d8cd98f00b204e9800998ecf8427e</code>.
     */
    // TODO: @Test
    public void md5EmptyTestVector() {
        Md5FunctionBuilder builder = new Md5FunctionBuilder();
        builder.inputVectorName("i");
        builder.outputVectorName("o");
        VectorialFunction md5 = builder.build();
        Word inputVector = new Word("i", 512);
        ModifiableMap<Variable, Expression> message = ModifiableMap.empty();
        message.add(inputVector.getAt(0), LiteralExpression.TRUE);
        for (int i = 1; i < 512; i++) {
            message.add(inputVector.getAt(i), LiteralExpression.FALSE);
        }
        VectorialFunction vf = md5.extendWih(message);
        Word outputVector = builder.getOutputVector();

        VectorialFunction resultA = vf.simplify(outputVector.getSlice(0, 32));
        ModifiableMap<Variable, Expression> expected = ModifiableMap.empty();
        expected.addAll(VectorialFunctionBuilder.buildAssignmentFunctions(outputVector.getSlice(0, 32), 0xd41d8cd9L));
        VectorialFunction e = new VectorialFunction(expected);
        assertTrue(e.containsSame(resultA));

        VectorialFunction resultB = vf.simplify(outputVector.getSlice(32, 64));
        ModifiableMap<Variable, Expression> expectedB = ModifiableMap.empty();
        expectedB.addAll(VectorialFunctionBuilder.buildAssignmentFunctions(outputVector.getSlice(32, 64), 0x8f00b204L));
        VectorialFunction eb = new VectorialFunction(expectedB);
        assertTrue(eb.containsSame(resultB));

        VectorialFunction resultC = vf.simplify(outputVector.getSlice(64, 96));
        ModifiableMap<Variable, Expression> expectedC = ModifiableMap.empty();
        expectedC.addAll(VectorialFunctionBuilder.buildAssignmentFunctions(outputVector.getSlice(64, 96), 0xe9800998L));
        VectorialFunction ec = new VectorialFunction(expectedC);
        assertTrue(ec.containsSame(resultC));

        VectorialFunction resultD = vf.simplify(outputVector.getSlice(96, 128));
        ModifiableMap<Variable, Expression> expectedD = ModifiableMap.empty();
        expectedD
                .addAll(VectorialFunctionBuilder.buildAssignmentFunctions(outputVector.getSlice(96, 128), 0xecf8427eL));
        VectorialFunction ed = new VectorialFunction(expectedD);
        assertTrue(ed.containsSame(resultD));
    }
}
