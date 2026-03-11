package net.filipvanlaenen.iacaj.builders;

import net.filipvanlaenen.iacaj.expressions.Expression;
import net.filipvanlaenen.iacaj.expressions.IdentityExpression;
import net.filipvanlaenen.iacaj.expressions.Operator;
import net.filipvanlaenen.iacaj.expressions.Variable;
import net.filipvanlaenen.iacaj.expressions.VectorialFunction;
import net.filipvanlaenen.iacaj.expressions.Word;
import net.filipvanlaenen.kolektoj.Map;
import net.filipvanlaenen.kolektoj.ModifiableMap;
import net.filipvanlaenen.nombrajkolektoj.integers.OrderedIntegerCollection;
import net.filipvanlaenen.nombrajkolektoj.longs.OrderedLongCollection;

/**
 * A builder class for the MD5 function. In particular, it provides the vectorial Boolean function to process the first
 * 512-bit chunk of a message. This means that the internal 128-bit state (A, B, C, and D) is set to its initial value.
 *
 * @see <a href="https://en.wikipedia.org/wiki/MD5#Pseudocode">MD5 - Wikipedia</a> for the pseudocode of the MD5
 *      algorithm.
 */
public class Md5FunctionBuilder extends VectorialFunctionBuilder {
    private Integer numberOfRounds = 64;
    private Word outputVector;

    @Override
    public VectorialFunction build() throws IllegalStateException {
        super.prebuild();

        // s specifies the per-round shift amounts
        // s[ 0..15] := { 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22 }
        // s[16..31] := { 5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20 }
        // s[32..47] := { 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23 }
        // s[48..63] := { 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21 }
        OrderedIntegerCollection s = OrderedIntegerCollection.of(7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17,
                22, 5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23,
                4, 11, 16, 23, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21);

        // Use binary integer part of the sines of integers (Radians) as constants:
        // for i from 0 to 63 do
        // . . K[i] := floor(232 × abs(sin(i + 1)))
        // end for
        // (Or just use the following precomputed table):
        // K[ 0.. 3] := { 0xd76aa478, 0xe8c7b756, 0x242070db, 0xc1bdceee }
        // K[ 4.. 7] := { 0xf57c0faf, 0x4787c62a, 0xa8304613, 0xfd469501 }
        // K[ 8..11] := { 0x698098d8, 0x8b44f7af, 0xffff5bb1, 0x895cd7be }
        // K[12..15] := { 0x6b901122, 0xfd987193, 0xa679438e, 0x49b40821 }
        // K[16..19] := { 0xf61e2562, 0xc040b340, 0x265e5a51, 0xe9b6c7aa }
        // K[20..23] := { 0xd62f105d, 0x02441453, 0xd8a1e681, 0xe7d3fbc8 }
        // K[24..27] := { 0x21e1cde6, 0xc33707d6, 0xf4d50d87, 0x455a14ed }
        // K[28..31] := { 0xa9e3e905, 0xfcefa3f8, 0x676f02d9, 0x8d2a4c8a }
        // K[32..35] := { 0xfffa3942, 0x8771f681, 0x6d9d6122, 0xfde5380c }
        // K[36..39] := { 0xa4beea44, 0x4bdecfa9, 0xf6bb4b60, 0xbebfbc70 }
        // K[40..43] := { 0x289b7ec6, 0xeaa127fa, 0xd4ef3085, 0x04881d05 }
        // K[44..47] := { 0xd9d4d039, 0xe6db99e5, 0x1fa27cf8, 0xc4ac5665 }
        // K[48..51] := { 0xf4292244, 0x432aff97, 0xab9423a7, 0xfc93a039 }
        // K[52..55] := { 0x655b59c3, 0x8f0ccc92, 0xffeff47d, 0x85845dd1 }
        // K[56..59] := { 0x6fa87e4f, 0xfe2ce6e0, 0xa3014314, 0x4e0811a1 }
        // K[60..63] := { 0xf7537e82, 0xbd3af235, 0x2ad7d2bb, 0xeb86d391 }
        OrderedLongCollection k = OrderedLongCollection.of(0xD76AA478L, 0xE8C7B756L, 0x242070DBL, 0xC1BDCEEEL,
                0xF57C0FAFL, 0x4787C62AL, 0xA8304613L, 0xFD469501L, 0x698098D8L, 0x8B44F7AFL, 0xFFFF5BB1L, 0x895CD7BEL,
                0x6B901122L, 0xFD987193L, 0xA679438EL, 0x49B40821L, 0xF61E2562L, 0xC040B340L, 0x265E5A51L, 0xE9B6C7AAL,
                0xD62F105DL, 0x02441453L, 0xD8A1E681L, 0xE7D3FBC8L, 0x21E1CDE6L, 0xC33707D6L, 0xF4D50D87L, 0x455A14EDL,
                0xA9E3E905L, 0xFCEFA3F8L, 0x676F02D9L, 0x8D2A4C8AL, 0xFFFA3942L, 0x8771F681L, 0x6D9D6122L, 0xFDE5380CL,
                0xA4BEEA44L, 0x4BDECFA9L, 0xF6BB4B60L, 0xBEBFBC70L, 0x289B7EC6L, 0xEAA127FAL, 0xD4EF3085L, 0x04881D05L,
                0xD9D4D039L, 0xE6DB99E5L, 0x1FA27CF8L, 0xC4AC5665L, 0xF4292244L, 0x432AFF97L, 0xAB9423A7L, 0xFC93A039L,
                0x655B59C3L, 0x8F0CCC92L, 0xFFEFF47DL, 0x85845DD1L, 0x6FA87E4FL, 0xFE2CE6E0L, 0xA3014314L, 0x4E0811A1L,
                0xF7537E82L, 0xBD3AF235L, 0x2AD7D2BBL, 0xEB86D391L);

        // Initialize variables:
        // var int a0 := 0x67452301 // A
        // var int b0 := 0xefcdab89 // B
        // var int c0 := 0x98badcfe // C
        // var int d0 := 0x10325476 // D
        Long a0 = 0x67452301L;
        Long b0 = 0xEFCDAB89L;
        Long c0 = 0x98BADCFEL;
        Long d0 = 0x10325476L;

        ModifiableMap<Variable, Expression> map = ModifiableMap.empty();

        // Process the first 512-bit chunk of the message:
        // break chunk into sixteen 32-bit words M[j], 0 ≤ j ≤ 15
        Word outerInputVector = new Word(getInputVectorName(), 512);
        Word innerInputVector = new Word("ii", 512);
        for (int j = 0; j < 16; j++) {
            for (int i = 0; i < 4; i++) {
                for (int h = 0; h < 8; h++) {
                    int ii = h + 8 * (3 - i) + 32 * j;
                    int oi = h + 8 * i + 32 * j;
                    map.add(innerInputVector.getAt(ii), new IdentityExpression(outerInputVector.getAt(oi)));
                }
            }
        }

        // Initialize hash value for this chunk:
        // var int A := a0
        Word a00 = new Word("a00", 32, false);
        map.addAll(buildAssignmentFunctions(a00, a0));
        Word a = a00;
        // var int B := b0
        Word b00 = new Word("b00", 32, false);
        map.addAll(buildAssignmentFunctions(b00, b0));
        Word b = b00;
        // var int C := c0
        Word c00 = new Word("c00", 32, false);
        map.addAll(buildAssignmentFunctions(c00, c0));
        Word c = c00;
        // var int D := d0
        Word d00 = new Word("d00", 32, false);
        map.addAll(buildAssignmentFunctions(d00, d0));
        Word d = d00;

        // Main loop:
        // for i from 0 to 63 do
        for (int i = 0; i <= 63 && i < numberOfRounds; i++) {
            String round = String.format("%02d", i + 1);
            int g = 0;
            // var int F, g
            // if 0 ≤ i ≤ 15 then
            Word fz = new Word("fz" + round, 32, false);
            if (0 <= i && i <= 15) {
                // F := (B and C) or ((not B) and D)
                Word fa = new Word("fa" + round, 32, false);
                map.addAll(buildOperationFunctions(Operator.AND, fa, b, c));
                Word fb = new Word("fb" + round, 32, false);
                map.addAll(buildNegationFunctions(b, fb));
                Word fc = new Word("fc" + round, 32, false);
                map.addAll(buildOperationFunctions(Operator.AND, fc, fb, d));
                map.addAll(buildOperationFunctions(Operator.OR, fz, fa, fc));
                // g := i
                g = i;
            }
            // else if 16 ≤ i ≤ 31 then
            else if (16 <= i && i <= 31) {
                // F := (D and B) or ((not D) and C)
                Word fa = new Word("fa" + round, 32, false);
                map.addAll(buildOperationFunctions(Operator.AND, fa, d, b));
                Word fb = new Word("fb" + round, 32, false);
                map.addAll(buildNegationFunctions(d, fb));
                Word fc = new Word("fc" + round, 32, false);
                map.addAll(buildOperationFunctions(Operator.AND, fc, fb, c));
                map.addAll(buildOperationFunctions(Operator.OR, fz, fa, fc));
                // g := (5×i + 1) mod 16
                g = (5 * i + 1) % 16;
            }
            // else if 32 ≤ i ≤ 47 then
            else if (32 <= i && i <= 47) {
                // F := B xor C xor D
                map.addAll(buildOperationFunctions(Operator.XOR, fz, b, c, d));
                // g := (3×i + 5) mod 16
                g = (3 * i + 5) % 16;
            }
            // else if 48 ≤ i ≤ 63 then
            else if (48 <= i && i <= 63) {
                // F := C xor (B or (not D))
                Word fa = new Word("fa" + round, 32, false);
                map.addAll(buildNegationFunctions(d, fa));
                Word fb = new Word("fb" + round, 32, false);
                map.addAll(buildOperationFunctions(Operator.OR, fb, b, fa));
                map.addAll(buildOperationFunctions(Operator.XOR, fz, c, fb));
                // g := (7×i) mod 16
                g = (7 * i) % 16;
            }

            // Be wary of the below definitions of a,b,c,d
            // F := F + A + K[i] + M[g] // M[g] must be a 32-bit block
            Word fy = new Word("fy" + round, 32, false);
            map.addAll(buildAdditionFunctions(fz, a, fy));
            Word ki = new Word("k" + round, 32, false);
            map.addAll(buildAssignmentFunctions(ki, k.getAt(i)));
            Word fx = new Word("fx" + round, 32, false);
            map.addAll(buildAdditionFunctions(fy, ki, fx));
            Word f = new Word("f" + round, 32, false);
            map.addAll(buildAdditionFunctions(fx, innerInputVector.getSlice(g * 32, (g + 1) * 32), f));

            // A := D
            a = new Word("a" + round, 32, false);
            map.addAll(buildIdentityFunctions(d, a));
            // D := C
            d = new Word("d" + round, 32, false);
            map.addAll(buildIdentityFunctions(c, d));
            // C := B
            c = new Word("c" + round, 32, false);
            map.addAll(buildIdentityFunctions(b, c));
            // B := B + leftrotate(F, s[i])
            Word fr = new Word("fr" + round, 32, false);
            map.addAll(buildRotationFunctions(f, -s.getAt(i), fr));
            Word bNew = new Word("b" + round, 32, false);
            map.addAll(buildAdditionFunctions(b, fr, bNew));
            b = bNew;
        }
        // end for

        // Add this chunk's hash to result so far:
        // a0 := a0 + A
        Word a0z = new Word("a0z", 32, false);
        map.addAll(buildAdditionFunctions(a00, a, a0z));
        // b0 := b0 + B
        Word b0z = new Word("b0z", 32, false);
        map.addAll(buildAdditionFunctions(b00, b, b0z));
        // c0 := c0 + C
        Word c0z = new Word("c0z", 32, false);
        map.addAll(buildAdditionFunctions(c00, c, c0z));
        // d0 := d0 + D
        Word d0z = new Word("d0z", 32, false);
        map.addAll(buildAdditionFunctions(d00, d, d0z));

        // var char digest[16] := a0 append b0 append c0 append d0
        outputVector = new Word(getOutputVectorName(), 128);
        map.addAll(addOutputChunk(outputVector, a0z, 0));
        map.addAll(addOutputChunk(outputVector, b0z, 1));
        map.addAll(addOutputChunk(outputVector, c0z, 2));
        map.addAll(addOutputChunk(outputVector, d0z, 3));

        return new VectorialFunction(map);
    }

    private Map<Variable, Expression> addOutputChunk(Word ov, Word c, int pos) {
        ModifiableMap<Variable, Expression> map = ModifiableMap.empty();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                map.add(ov.getAt(32 * pos + 8 * i + j), new IdentityExpression(c.getAt(8 * (3 - i) + j)));
            }
        }
        return map;
    }

    @Override
    public Word getOutputVector() {
        return outputVector;
    }

    public void setNumberOfRounds(Integer numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }
}
