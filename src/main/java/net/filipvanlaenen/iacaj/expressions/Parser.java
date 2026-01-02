package net.filipvanlaenen.iacaj.expressions;

import net.filipvanlaenen.kolektoj.ModifiableCollection;
import net.filipvanlaenen.kolektoj.OrderedCollection;
import net.filipvanlaenen.kolektoj.ValueCollection;

/**
 * Class parsing strings into expressions.
 */
public final class Parser {
    /**
     * The string representing the and operator.
     */
    private static final String AND = "∧";
    /**
     * The string representing the negation operator.
     */
    private static final String NEGATION = "¬";
    /**
     * The string representing the or operator.
     */
    private static final String OR = "∨";
    /**
     * The string representing the xor operator.
     */
    private static final String XOR = "⊻";

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Parser() {
    }

    /**
     * Extracts direct and negated variables from a string representing an expression with a symbol.
     *
     * @param s      The string to extract from.
     * @param symbol The symbol used in the expression.
     * @return An ordered collection of size two, containing the direct variables in the first position and the negated
     *         variables in the second position.
     */
    private static OrderedCollection<ValueCollection<Variable>> extractVariables(final String s, final String symbol) {
        ModifiableCollection<Variable> directVariables = ModifiableCollection.empty();
        ModifiableCollection<Variable> negatedVariables = ModifiableCollection.empty();
        for (String operand : s.split(symbol)) {
            if (operand.contains(NEGATION)) {
                negatedVariables.add(new Variable(operand.trim().substring(1)));
            } else {
                directVariables.add(new Variable(operand.trim()));
            }
        }
        // TODO: Refactor after the implementation of https://github.com/filipvanlaenen/kolektoj/issues/108
        Variable[] direct = directVariables.toArray(new Variable[0]);
        Variable[] negated = negatedVariables.toArray(new Variable[0]);
        return OrderedCollection.<ValueCollection<Variable>>of(ValueCollection.of(direct), ValueCollection.of(negated));
    }

    /**
     * Parses a string into an expression.
     *
     * @param s The string to be parsed.
     * @return An expression representing the content of the provided string.
     */
    public static Expression parse(final String s) {
        if (s.contentEquals("true")) {
            return LiteralExpression.TRUE;
        } else if (s.contentEquals("false")) {
            return LiteralExpression.FALSE;
        } else if (s.contains(AND)) {
            OrderedCollection<ValueCollection<Variable>> variables = extractVariables(s, AND);
            return new AndExpression(variables.getAt(0), variables.getAt(1));
        } else if (s.contains(OR)) {
            OrderedCollection<ValueCollection<Variable>> variables = extractVariables(s, OR);
            return new OrExpression(variables.getAt(0), variables.getAt(1));
        } else if (s.contains(XOR)) {
            OrderedCollection<ValueCollection<Variable>> variables = extractVariables(s, XOR);
            // TODO: Refactor after the implementation of https://github.com/filipvanlaenen/kolektoj/issues/99
            ModifiableCollection<Variable> directVariables = ModifiableCollection.of(variables.getAt(0));
            directVariables.addAll(variables.getAt(1));
            // TODO: Refactor after the implementation of https://github.com/filipvanlaenen/kolektoj/issues/108
            Variable[] directArray = directVariables.toArray(new Variable[0]);
            ValueCollection<Variable> direct = ValueCollection.of(directArray);
            return new XorExpression(direct, variables.getAt(1).size() % 2 == 1);
        } else if (s.startsWith(NEGATION)) {
            return new NegationExpression(new Variable(s.substring(1)));
        } else {
            return new IdentityExpression(new Variable(s));
        }
    }
}
