package net.filipvanlaenen.iacaj.expressions;

import static net.filipvanlaenen.iacaj.expressions.Operator.*;

import net.filipvanlaenen.kolektoj.ModifiableCollection;
import net.filipvanlaenen.kolektoj.OrderedCollection;
import net.filipvanlaenen.kolektoj.ValueCollection;

/**
 * Class parsing strings into expressions.
 */
public final class Parser {
    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Parser() {
    }

    private static boolean containsSymbol(final String s, final Operator operator) {
        return s.contains(operator.getSymbol());
    }

    /**
     * Extracts direct and negated variables from a string representing an expression with a symbol.
     *
     * @param s        The string to extract from.
     * @param operator The operator used in the expression.
     * @return An ordered collection of size two, containing the direct variables in the first position and the negated
     *         variables in the second position.
     */
    private static OrderedCollection<ValueCollection<Variable>> extractVariables(final String s,
            final Operator operator) {
        ModifiableCollection<Variable> directVariables = ModifiableCollection.empty();
        ModifiableCollection<Variable> negatedVariables = ModifiableCollection.empty();
        for (String operand : s.split(operator.getSymbol())) {
            if (operand.contains(NOT.getSymbol())) {
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
        } else if (containsSymbol(s, AND)) {
            OrderedCollection<ValueCollection<Variable>> variables = extractVariables(s, AND);
            return new AndFunction(variables.getAt(0), variables.getAt(1));
        } else if (containsSymbol(s, OR)) {
            OrderedCollection<ValueCollection<Variable>> variables = extractVariables(s, OR);
            return new OrFunction(variables.getAt(0), variables.getAt(1));
        } else if (containsSymbol(s, XOR)) {
            OrderedCollection<ValueCollection<Variable>> variables = extractVariables(s, XOR);
            // TODO: Refactor after the implementation of https://github.com/filipvanlaenen/kolektoj/issues/99
            ModifiableCollection<Variable> directVariables = ModifiableCollection.of(variables.getAt(0));
            directVariables.addAll(variables.getAt(1));
            // TODO: Refactor after the implementation of https://github.com/filipvanlaenen/kolektoj/issues/108
            Variable[] directArray = directVariables.toArray(new Variable[0]);
            ValueCollection<Variable> direct = ValueCollection.of(directArray);
            return new XorFunction(direct, variables.getAt(1).size() % 2 == 1);
        } else if (s.startsWith(NOT.getSymbol())) {
            return new NegationExpression(new Variable(s.substring(1)));
        } else {
            return new IdentityExpression(new Variable(s));
        }
    }
}
