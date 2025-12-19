package net.filipvanlaenen.iacaj.expressions;

import net.filipvanlaenen.kolektoj.ModifiableCollection;
import net.filipvanlaenen.kolektoj.OrderedCollection;
import net.filipvanlaenen.kolektoj.ValueCollection;

/**
 * Class parsing strings into expressions.
 */
public class Parser {
    private static final String AND = "∧";
    private static final String NEGATION = "¬";
    private static final String OR = "∨";
    private static final String XOR = "⊻";

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
            OrderedCollection<ValueCollection<Variable>> variables = extracted(s, AND);
            return new AndExpression(variables.getAt(0), variables.getAt(1));
        } else if (s.contains(OR)) {
            OrderedCollection<ValueCollection<Variable>> variables = extracted(s, OR);
            return new OrExpression(variables.getAt(0), variables.getAt(1));
        } else if (s.contains(XOR)) {
            OrderedCollection<ValueCollection<Variable>> variables = extracted(s, XOR);
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

    private static OrderedCollection<ValueCollection<Variable>> extracted(final String s, final String symbol) {
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
}
