package net.filipvanlaenen.iacaj;

import java.math.BigInteger;

/**
 * Class producing a Boolean function for the SHA-256 hash function.
 * 
 * The pseudocode can be found here:
 * https://en.wikipedia.org/wiki/SHA-2#Pseudocode
 */
public class Sha256Producer {
    public class Word {
        private final String[] variables;

        public Word(int wordLength) {
            variables = new String[wordLength];
        }

        public void put(int i, String name) {
            variables[i] = name;
        }

        public String get(int i) {
            return variables[i];
        }
    }

    private static final int WORD_LENGTH = 32;

    private int numberOfRounds = 64;
    private long vCounter = 0;
    private Word a, b, c, d, e, f, g, h;
    private Word h0, h1, h2, h3, h4, h5, h6, h7;
    private Word[] k = new Word[64];

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

    private void appendWordToResult(BooleanFunction bf, Word w, int offset) {
        for (int i = 1; i <= WORD_LENGTH; i++) {
            bf.addExpression(new BooleanOperation("o" + (WORD_LENGTH * offset + i), w.get(WORD_LENGTH - i)));
        }
    }

    private void composeResult(BooleanFunction bf) {
        Word[] words = new Word[] {h0, h1, h2, h3, h4, h5, h6, h7};
        for (int i = 0; i < words.length; i++) {
            appendWordToResult(bf, words[i], i);
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
        for (int i = 0; i < numberOfRounds; i++) {
            k[i] = addConstant(bf, values[i]);
        }
    }

    /**
     * Produces a Boolean function for the SHA-256 hash function.
     */
    public BooleanFunction produce() {
        BooleanFunction result = new BooleanFunction();
        initializeH(result);
        initializeK(result);
        createW();
        initializeAtoH();
        for (int i = 0; i < numberOfRounds; i++) {
            compress();
        }
        addCompressionResultToHash(result);
        composeResult(result);
        return result;
    }

    private void compress() {
        /**
         * S1 := (e rightrotate 6) xor (e rightrotate 11) xor (e rightrotate 25)
         * 
         * ch := (e and f) xor ((not e) and g)
         * 
         * temp1 := h + S1 + ch + k[i] + w[i]
         *
         * S0 := (a rightrotate 2) xor (a rightrotate 13) xor (a rightrotate 22)
         *
         * maj := (a and b) xor (a and c) xor (b and c)
         *
         * temp2 := S0 + maj
         *
         * h := g
         *
         * g := f
         *
         * f := e
         *
         * e := d + temp1
         *
         * d := c
         *
         * c := b
         *
         * b := a
         *
         * a := temp1 + temp2
         */
    }

    private void createW() {
        /**
         * create a 64-entry message schedule array w[0..63] of 32-bit words (The
         * initial values in w[0..63] don't matter, so many implementations zero them
         * here)
         *
         * copy chunk into first 16 words w[0..15] of the message schedule array
         *
         * Extend the first 16 words into the remaining 48 words w[16..63] of the
         * message schedule array:
         * 
         * for i from 16 to 63
         * 
         * s0 := (w[i-15] rightrotate 7) xor (w[i-15] rightrotate 18) xor (w[i-15]
         * rightshift 3)
         * 
         * s1 := (w[i- 2] rightrotate 17) xor (w[i- 2] rightrotate 19) xor (w[i- 2]
         * rightshift 10)
         * 
         * w[i] := w[i-16] + s0 + w[i-7] + s1
         */
    }

}
