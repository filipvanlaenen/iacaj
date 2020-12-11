package net.filipvanlaenen.iacaj.producer;

import java.math.BigInteger;

import net.filipvanlaenen.iacaj.BooleanFunction;
import net.filipvanlaenen.iacaj.BooleanOperation;
import net.filipvanlaenen.iacaj.BooleanOperation.Operator;

/**
 * Class producing a Boolean function for the SHA-256 hash function.
 * 
 * The pseudocode can be found here:
 * https://en.wikipedia.org/wiki/SHA-2#Pseudocode
 */
public class Sha256Producer {
    public class Word {
        private final String[] variables;
        private final int length;

        public Word(int length) {
            this.length = length;
            variables = new String[length];
        }

        public void put(int i, String name) {
            variables[i] = name;
        }

        public String get(int i) {
            return variables[i];
        }

        int getLength() {
            return length;
        }

        public Word rightRotate(int r) {
            Word result = new Word(length);
            for (int i = 0; i < length; i++) {
                result.put(i, get((i + r) % length));
            }
            return result;
        }
    }

    private static final int WORD_LENGTH = 32;

    private int numberOfRounds = 64;
    private long vCounter = 0;
    private Word a, b, c, d, e, f, g, h;
    private Word h0, h1, h2, h3, h4, h5, h6, h7;
    private Word[] k;
    private Word[] w;

    private void addCompressionResultToHash(BooleanFunction bf) {
        h0 = addWords(bf, h0, a);
        h1 = addWords(bf, h1, b);
        h2 = addWords(bf, h2, c);
        h3 = addWords(bf, h3, d);
        h4 = addWords(bf, h4, e);
        h5 = addWords(bf, h5, f);
        h6 = addWords(bf, h6, g);
        h7 = addWords(bf, h7, h);
    }

    private Word addWords(BooleanFunction bf, Word w0, Word w1) {
        Word result = new Word(WORD_LENGTH);
        BooleanOperation b0 = new BooleanOperation("v" + (++vCounter), w0.get(0) + " ⊻ " + w1.get(0));
        bf.addExpression(b0);
        result.put(0, b0.getName());
        BooleanOperation c = new BooleanOperation("v" + (++vCounter), w0.get(0) + " ∧ " + w1.get(0));
        bf.addExpression(c);
        for (int i = 1; i < WORD_LENGTH - 1; i++) {
            BooleanOperation bi = new BooleanOperation("v" + (++vCounter),
                    w0.get(i) + " ⊻ " + w1.get(i) + " ⊻ " + c.getName());
            bf.addExpression(bi);
            result.put(i, bi.getName());
            BooleanOperation p1 = new BooleanOperation("v" + (++vCounter), w0.get(0) + " ∧ " + w1.get(0));
            bf.addExpression(p1);
            BooleanOperation p2 = new BooleanOperation("v" + (++vCounter), w0.get(0) + " ⊻ " + w1.get(0));
            bf.addExpression(p2);
            BooleanOperation p3 = new BooleanOperation("v" + (++vCounter), c.getName() + " ∧ " + p2.getName());
            bf.addExpression(p3);
            c = new BooleanOperation("v" + (++vCounter), p1.getName() + " ⊻ " + p3.getName());
            bf.addExpression(c);
        }
        BooleanOperation bl = new BooleanOperation("v" + (++vCounter),
                w0.get(WORD_LENGTH - 1) + " ⊻ " + w1.get(WORD_LENGTH - 1) + " ⊻ " + c.getName());
        bf.addExpression(bl);
        result.put(WORD_LENGTH - 1, bl.getName());
        return result;
    }

    private Word addConstant(BooleanFunction bf, String hexValue) {
        BigInteger value = new BigInteger(hexValue, 16);
        Word result = new Word(WORD_LENGTH);
        for (int i = 0; i < WORD_LENGTH; i++) {
            String rightHandSide;
            if (value.mod(new BigInteger("2")).equals(BigInteger.ZERO)) {
                rightHandSide = "False";
            } else {
                rightHandSide = "True";
            }
            BooleanOperation bo = new BooleanOperation("v" + (++vCounter), rightHandSide);
            bf.addExpression(bo);
            result.put(i, bo.getName());
            value = value.divide(new BigInteger("2"));
        }
        return result;
    }

    private Word andWords(BooleanFunction bf, Word... words) {
        return atomicOperationOnWords(bf, Operator.And, words);
    }

    private void appendWordToResult(BooleanFunction bf, Word w, int offset) {
        for (int i = 1; i <= WORD_LENGTH; i++) {
            bf.addExpression(new BooleanOperation("o" + (WORD_LENGTH * offset + i), w.get(WORD_LENGTH - i)));
        }
    }

    private Word atomicOperationOnWords(BooleanFunction bf, Operator o, Word... words) {
        Word result = new Word(WORD_LENGTH);
        for (int i = 1; i < WORD_LENGTH - 1; i++) {
            String[] operands = new String[words.length];
            for (int j = 0; j < words.length; j++) {
                operands[j] = words[j].get(i);
            }
            BooleanOperation bo = new BooleanOperation("v" + (++vCounter),
                    String.join(" " + o.getSymbol() + " ", operands));
            bf.addExpression(bo);
            result.put(i, bo.getName());
        }
        return result;
    }

    private void composeResult(BooleanFunction bf) {
        Word[] words = new Word[] {h0, h1, h2, h3, h4, h5, h6, h7};
        for (int i = 0; i < words.length; i++) {
            appendWordToResult(bf, words[i], i);
        }
    }

    private void compress(BooleanFunction bf, int i) {
        Word s1 = xorWords(bf, e.rightRotate(6), e.rightRotate(11), e.rightRotate(25));
        Word ch = xorWords(bf, andWords(bf, e, f), andWords(bf, negateWord(bf, e), g));
        Word t1 = addWords(bf, addWords(bf, addWords(bf, addWords(bf, h, s1), ch), k[i]), w[i]);
        Word s0 = xorWords(bf, a.rightRotate(2), a.rightRotate(13), a.rightRotate(22));
        Word maj = xorWords(bf, andWords(bf, a, b), andWords(bf, a, c), andWords(bf, b, c));
        Word t2 = addWords(bf, s0, maj);
        h = g;
        g = f;
        f = e;
        e = addWords(bf, d, t1);
        d = c;
        c = b;
        b = a;
        a = addWords(bf, t1, t2);
    }

    private void createW(BooleanFunction bf) {
        w = new Word[numberOfRounds];
        for (int i = 0; i < 16 && i < numberOfRounds; i++) {
            w[i] = new Word(WORD_LENGTH);
            for (int j = 0; j < WORD_LENGTH; j++) {
                w[i].put(j, "i" + (((i + 1) * WORD_LENGTH) - j));
            }
        }
        for (int i = 16; i < numberOfRounds; i++) {
            Word s0 = xorWords(bf, w[i - 15].rightRotate(7), w[i - 15].rightRotate(18), rightShift(bf, w[i - 15], 3));
            Word s1 = xorWords(bf, w[i - 2].rightRotate(17), w[i - 2].rightRotate(19), rightShift(bf, w[i - 2], 10));
            w[i] = addWords(bf, addWords(bf, addWords(bf, w[i - 16], s0), w[i - 7]), s1);
        }
    }

    private void initializeAtoH() {
        a = h0;
        b = h1;
        c = h2;
        d = h3;
        e = h4;
        f = h5;
        g = h6;
        h = h7;
    }

    private void initializeH(BooleanFunction bf) {
        h0 = addConstant(bf, "6a09e667");
        h1 = addConstant(bf, "bb67ae85");
        h2 = addConstant(bf, "3c6ef372");
        h3 = addConstant(bf, "a54ff53a");
        h4 = addConstant(bf, "510e527f");
        h5 = addConstant(bf, "9b05688c");
        h6 = addConstant(bf, "1f83d9ab");
        h7 = addConstant(bf, "5be0cd19");
    }

    private void initializeK(BooleanFunction bf) {
        String[] values = new String[] {"428a2f98", "71374491", "b5c0fbcf", "e9b5dba5", "3956c25b", "59f111f1",
                "923f82a4", "ab1c5ed5", "d807aa98", "12835b01", "243185be", "550c7dc3", "72be5d74", "80deb1fe",
                "9bdc06a7", "c19bf174", "e49b69c1", "efbe4786", "0fc19dc6", "240ca1cc", "2de92c6f", "4a7484aa",
                "5cb0a9dc", "76f988da", "983e5152", "a831c66d", "b00327c8", "bf597fc7", "c6e00bf3", "d5a79147",
                "06ca6351", "14292967", "27b70a85", "2e1b2138", "4d2c6dfc", "53380d13", "650a7354", "766a0abb",
                "81c2c92e", "92722c85", "a2bfe8a1", "a81a664b", "c24b8b70", "c76c51a3", "d192e819", "d6990624",
                "f40e3585", "106aa070", "19a4c116", "1e376c08", "2748774c", "34b0bcb5", "391c0cb3", "4ed8aa4a",
                "5b9cca4f", "682e6ff3", "748f82ee", "78a5636f", "84c87814", "8cc70208", "90befffa", "a4506ceb",
                "bef9a3f7", "c67178f2"};
        k = new Word[numberOfRounds];
        for (int i = 0; i < numberOfRounds; i++) {
            k[i] = addConstant(bf, values[i]);
        }
    }

    private Word negateWord(BooleanFunction bf, Word w) {
        Word result = new Word(WORD_LENGTH);
        for (int i = 1; i < WORD_LENGTH - 1; i++) {
            BooleanOperation bo = new BooleanOperation("v" + (++vCounter), "¬" + w.get(i));
            bf.addExpression(bo);
            result.put(i, bo.getName());
        }
        return result;
    }

    /**
     * Produces a Boolean function for the SHA-256 hash function.
     */
    public BooleanFunction produce() {
        BooleanFunction result = new BooleanFunction();
        initializeH(result);
        initializeK(result);
        createW(result);
        initializeAtoH();
        for (int i = 0; i < numberOfRounds; i++) {
            compress(result, i);
        }
        addCompressionResultToHash(result);
        composeResult(result);
        return result;
    }

    private Word rightShift(BooleanFunction bf, Word w, int r) {
        int length = w.getLength();
        Word result = new Word(length);
        for (int i = 0; i < length - r; i++) {
            result.put(i, w.get(i + r));
        }
        for (int i = length - r; i < length; i++) {
            BooleanOperation bo = new BooleanOperation("v" + (++vCounter), "False");
            bf.addExpression(bo);
            result.put(i, bo.getName());
        }
        return result;
    }

    private Word xorWords(BooleanFunction bf, Word... words) {
        return atomicOperationOnWords(bf, Operator.Xor, words);
    }
}
