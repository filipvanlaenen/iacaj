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
}
