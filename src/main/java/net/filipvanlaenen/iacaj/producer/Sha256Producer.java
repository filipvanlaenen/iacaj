package net.filipvanlaenen.iacaj.producer;

import java.math.BigInteger;
import java.util.List;

import net.filipvanlaenen.iacaj.BooleanFunction;
import net.filipvanlaenen.iacaj.BooleanOperation;
import net.filipvanlaenen.iacaj.BooleanOperation.Operator;

/**
 * Class producing a Boolean function for the SHA-256 hash function.
 *
 * The pseudocode can be found here:
 * https://en.wikipedia.org/wiki/SHA-2#Pseudocode
 */
public final class Sha256Producer extends Producer {
    /**
     * Word length for the SHA-256 algorithm.
     */
    static final int WORD_LENGTH = 32;
    /**
     * The default number of rounds for the SHA-256 algorithm.
     */
    private static final int DEFAULT_NUMBER_OF_ROUNDS = 64;
    /**
     * The magic number 3.
     */
    private static final int THREE = 3;
    /**
     * The magic number 6.
     */
    private static final int SIX = 6;
    /**
     * The magic number 7.
     */
    private static final int SEVEN = 7;
    /**
     * The magic number 10.
     */
    private static final int TEN = 10;
    /**
     * The magic number 11.
     */
    private static final int ELEVEN = 11;
    /**
     * The magic number 13.
     */
    private static final int THIRTEEN = 13;
    /**
     * The magic number 15.
     */
    private static final int FIFTEEN = 15;
    /**
     * The magic number 16.
     */
    private static final int SIXTEEN = 16;
    /**
     * The magic number 17.
     */
    private static final int SEVENTEEN = 17;
    /**
     * The magic number 18.
     */
    private static final int EIGHTEEN = 18;
    /**
     * The magic number 19.
     */
    private static final int NINETEEN = 19;
    /**
     * The magic number 22.
     */
    private static final int TWENTY_TWO = 22;
    /**
     * The magic number 25.
     */
    private static final int TWENTY_FIVE = 25;
    /**
     * The number of rounds for the SHA-256 algorithm.
     */
    private int numberOfRounds;
    /**
     * Working variables for SHA-256.
     */
    private Word a, b, c, d, e, f, g, h;
    /**
     * Hash values for SHA-256.
     */
    private Word h0, h1, h2, h3, h4, h5, h6, h7;
    /**
     * Array with round constants for SHA-256.
     */
    private Word[] k;
    /**
     * Message schedule array for SHA-256.
     */
    private Word[] w;

    /**
     * Creates a producer for SHA-256. If parameters are provided, the first
     * parameter is used as the number of rounds.
     *
     * @param parameters A list with parameters.
     */
    public Sha256Producer(final List<Integer> parameters) {
        if (parameters.isEmpty()) {
            this.numberOfRounds = DEFAULT_NUMBER_OF_ROUNDS;
        } else {
            this.numberOfRounds = parameters.get(0);
        }
    }

    /**
     * Adds the compression result to the hash.
     *
     * @param bf The Boolean function.
     */
    private void addCompressionResultToHash(final BooleanFunction bf) {
        h0 = addWords(bf, h0, a);
        h1 = addWords(bf, h1, b);
        h2 = addWords(bf, h2, c);
        h3 = addWords(bf, h3, d);
        h4 = addWords(bf, h4, e);
        h5 = addWords(bf, h5, f);
        h6 = addWords(bf, h6, g);
        h7 = addWords(bf, h7, h);
    }

    /**
     * Adds two words.
     *
     * @param bf The Boolean function.
     * @param w0 The first word.
     * @param w1 The second word.
     * @return A word holding the addition of the two words.
     */
    private Word addWords(final BooleanFunction bf, final Word w0, final Word w1) {
        Word result = new Word(WORD_LENGTH);
        BooleanOperation b0 = new BooleanOperation(getNextInternalVariableName(), w0.get(0) + " ⊻ " + w1.get(0));
        bf.addExpression(b0);
        result.put(0, b0.getName());
        BooleanOperation carry = new BooleanOperation(getNextInternalVariableName(), w0.get(0) + " ∧ " + w1.get(0));
        bf.addExpression(carry);
        for (int i = 1; i < WORD_LENGTH - 1; i++) {
            BooleanOperation bi = new BooleanOperation(getNextInternalVariableName(),
                    w0.get(i) + " ⊻ " + w1.get(i) + " ⊻ " + carry.getName());
            bf.addExpression(bi);
            result.put(i, bi.getName());
            BooleanOperation p1 = new BooleanOperation(getNextInternalVariableName(), w0.get(0) + " ∧ " + w1.get(0));
            bf.addExpression(p1);
            BooleanOperation p2 = new BooleanOperation(getNextInternalVariableName(), w0.get(0) + " ⊻ " + w1.get(0));
            bf.addExpression(p2);
            BooleanOperation p3 = new BooleanOperation(getNextInternalVariableName(),
                    carry.getName() + " ∧ " + p2.getName());
            bf.addExpression(p3);
            carry = new BooleanOperation(getNextInternalVariableName(), p1.getName() + " ⊻ " + p3.getName());
            bf.addExpression(carry);
        }
        BooleanOperation bl = new BooleanOperation(getNextInternalVariableName(),
                w0.get(WORD_LENGTH - 1) + " ⊻ " + w1.get(WORD_LENGTH - 1) + " ⊻ " + carry.getName());
        bf.addExpression(bl);
        result.put(WORD_LENGTH - 1, bl.getName());
        return result;
    }

    /**
     * Adds a constant to the Boolean function.
     *
     * @param bf       The Boolean function.
     * @param hexValue The constant to be added, in hexadecimal format.
     * @return A word holding the constant.
     */
    private Word addConstant(final BooleanFunction bf, final String hexValue) {
        BigInteger value = new BigInteger(hexValue, SIXTEEN);
        Word result = new Word(WORD_LENGTH);
        for (int i = 0; i < WORD_LENGTH; i++) {
            String rightHandSide;
            if (value.mod(new BigInteger("2")).equals(BigInteger.ZERO)) {
                rightHandSide = "False";
            } else {
                rightHandSide = "True";
            }
            BooleanOperation bo = new BooleanOperation(getNextInternalVariableName(), rightHandSide);
            bf.addExpression(bo);
            result.put(i, bo.getName());
            value = value.divide(new BigInteger("2"));
        }
        return result;
    }

    /**
     * Combines to words using AND.
     *
     * @param bf    The Boolean function.
     * @param words The words to be ANDed together.
     * @return A word holding the result.
     */
    private Word andWords(final BooleanFunction bf, final Word... words) {
        return atomicOperationOnWords(bf, Operator.And, words);
    }

    /**
     * Composes the result of the SHA-256 algorithm.
     *
     * @param bf The Boolean function.
     */
    private void composeResult(final BooleanFunction bf) {
        Word[] words = new Word[] {h0, h1, h2, h3, h4, h5, h6, h7};
        for (int i = 0; i < words.length; i++) {
            appendWordToOutput(bf, words[i], i);
        }
    }

    /**
     * Performs a round of the compress function of the SHA-256 algorithm.
     *
     * @param bf The Boolean function.
     * @param i  The round.
     */
    private void compress(final BooleanFunction bf, final int i) {
        Word s1 = xorWords(bf, e.rightRotate(SIX), e.rightRotate(ELEVEN), e.rightRotate(TWENTY_FIVE));
        Word ch = xorWords(bf, andWords(bf, e, f), andWords(bf, negateWord(bf, e), g));
        Word t1 = addWords(bf, addWords(bf, addWords(bf, addWords(bf, h, s1), ch), k[i]), w[i]);
        Word s0 = xorWords(bf, a.rightRotate(2), a.rightRotate(THIRTEEN), a.rightRotate(TWENTY_TWO));
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

    /**
     * Initializes the message schedule array w.
     *
     * @param bf The Boolean function.
     */
    private void createW(final BooleanFunction bf) {
        w = new Word[numberOfRounds];
        for (int i = 0; i < SIXTEEN && i < numberOfRounds; i++) {
            w[i] = new Word(WORD_LENGTH);
            for (int j = 0; j < WORD_LENGTH; j++) {
                w[i].put(j, "i" + ((i + 1) * WORD_LENGTH - j));
            }
        }
        for (int i = SIXTEEN; i < numberOfRounds; i++) {
            Word s0 = xorWords(bf, w[i - FIFTEEN].rightRotate(SEVEN), w[i - FIFTEEN].rightRotate(EIGHTEEN),
                    rightShift(bf, w[i - FIFTEEN], THREE));
            Word s1 = xorWords(bf, w[i - 2].rightRotate(SEVENTEEN), w[i - 2].rightRotate(NINETEEN),
                    rightShift(bf, w[i - 2], TEN));
            w[i] = addWords(bf, addWords(bf, addWords(bf, w[i - SIXTEEN], s0), w[i - SEVEN]), s1);
        }
    }

    @Override
    protected int getWordLength() {
        return WORD_LENGTH;
    }

    /**
     * Initializes the working variables.
     */
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

    /**
     * Initializes the hash values.
     *
     * @param bf The Boolean function.
     */
    private void initializeH(final BooleanFunction bf) {
        h0 = addConstant(bf, "6a09e667");
        h1 = addConstant(bf, "bb67ae85");
        h2 = addConstant(bf, "3c6ef372");
        h3 = addConstant(bf, "a54ff53a");
        h4 = addConstant(bf, "510e527f");
        h5 = addConstant(bf, "9b05688c");
        h6 = addConstant(bf, "1f83d9ab");
        h7 = addConstant(bf, "5be0cd19");
    }

    /**
     * Initializes the round constants.
     *
     * @param bf The Boolean function.
     */
    private void initializeK(final BooleanFunction bf) {
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

    /**
     * Returns the negation of a word.
     *
     * @param bf   The Boolean function.
     * @param word The word to be negated.
     * @return A word holding the negation of the word.
     */
    private Word negateWord(final BooleanFunction bf, final Word word) {
        Word result = new Word(WORD_LENGTH);
        for (int i = 0; i < WORD_LENGTH; i++) {
            BooleanOperation bo = new BooleanOperation(getNextInternalVariableName(), "¬" + word.get(i));
            bf.addExpression(bo);
            result.put(i, bo.getName());
        }
        return result;
    }

    @Override
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

    /**
     * Returns a word with all variables names shifted to the right with r
     * positions.
     *
     * @param bf   The Boolean function.
     * @param word The word to be shifted to the right.
     * @param r    The number of positions to shift.
     * @return A word with all variable names shifted to the right with r positions.
     */
    private Word rightShift(final BooleanFunction bf, final Word word, final int r) {
        int length = word.getLength();
        Word result = new Word(length);
        for (int i = 0; i < length - r; i++) {
            result.put(i, word.get(i + r));
        }
        for (int i = length - r; i < length; i++) {
            BooleanOperation bo = new BooleanOperation(getNextInternalVariableName(), "False");
            bf.addExpression(bo);
            result.put(i, bo.getName());
        }
        return result;
    }

    /**
     * Combines to words using XOR.
     *
     * @param bf    The Boolean function.
     * @param words The words to be XORed together.
     * @return A word holding the result.
     */
    private Word xorWords(final BooleanFunction bf, final Word... words) {
        return atomicOperationOnWords(bf, Operator.Xor, words);
    }
}
