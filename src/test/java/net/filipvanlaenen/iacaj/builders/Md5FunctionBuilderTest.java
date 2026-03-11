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
     * Unit test verifying that <code>MD5("") = d41d8cd98f00b204e9800998ecf8427e</code>.
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
        VectorialFunction result = vf.simplify(builder.getOutputVector());
        String output = result.toString();
        System.out.println(output);
        assertTrue(output.contains("o1 = true"));
    }

    /**
     * Unit test verifying that <code>MD5[0]("") = 0x77777777fdd2ed94878888887431eda8</code>.
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

        extracted(vf, "fz01", 0x98badcfeL);
        extracted(vf, "fy01", 0xffffffffL);
        extracted(vf, "k01", 0xd76aa478L);
        extracted(vf, "fx01", 0xd76aa477L);
        extracted(vf, inputVector.getSlice(0, 32), 0x80000000L);
        extracted(vf, "ii", 0x80000000L);
        extracted(vf, "f01", 0xd76aa4f7L);
        extracted(vf, "a01", 0x10325476L);
        extracted(vf, "d01", 0x98badcfeL);
        extracted(vf, "c01", 0xefcdab89L);
        extracted(vf, "fr01", 0xb5527bebL);
        extracted(vf, "b01", 0xa5202774L);
        extracted(vf, "a0z", 0x77777777L);
        extracted(vf, "b0z", 0x94edd2fdL);
        extracted(vf, "c0z", 0x88888887L);
        extracted(vf, "d0z", 0xa8ed3174L);

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
     * Unit test verifying that <code>MD5[1]("") = 0xffffffff663e63e57204db3dffffffff</code>.
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

    private void extracted(VectorialFunction vf, String n, long l) {
        Word intermediateResult = new Word(n, 32, false);
        extracted(vf, intermediateResult, l);
    }

    private void extracted(VectorialFunction vf, Word intermediateResult, long l) {
        VectorialFunction ir = vf.simplify(intermediateResult);
        ModifiableMap<Variable, Expression> expectedIr = ModifiableMap.empty();
        expectedIr.addAll(VectorialFunctionBuilder.buildAssignmentFunctions(intermediateResult, l));
        VectorialFunction irvf = new VectorialFunction(expectedIr);
        assertTrue(ir.containsSame(irvf));
    }
}
