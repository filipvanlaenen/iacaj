package net.filipvanlaenen.iacaj.expressions;

import static net.filipvanlaenen.iacaj.expressions.Operator.AND;
import static net.filipvanlaenen.iacaj.expressions.Operator.NOT;
import static net.filipvanlaenen.iacaj.expressions.Operator.OR;
import static net.filipvanlaenen.iacaj.expressions.Operator.XOR;

import net.filipvanlaenen.kolektoj.ModifiableCollection;
import net.filipvanlaenen.kolektoj.OrderedCollection;
import net.filipvanlaenen.kolektoj.ValueCollection;
import net.filipvanlaenen.kolektoj.collectors.Collectors;

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
     * Parses an expression string into an expression.
     *
     * @param expressionString The string to be parsed.
     * @return An expression representing the content of the provided string.
     */
    public static Expression parseExpression(final String expressionString) {
        if (expressionString.contentEquals("true")) {
            return LiteralExpression.TRUE;
        } else if (expressionString.contentEquals("false")) {
            return LiteralExpression.FALSE;
        } else if (containsSymbol(expressionString, AND)) {
            OrderedCollection<ValueCollection<Variable>> variables = extractVariables(expressionString, AND);
            return new AndFunction(variables.getAt(0), variables.getAt(1));
        } else if (containsSymbol(expressionString, OR)) {
            OrderedCollection<ValueCollection<Variable>> variables = extractVariables(expressionString, OR);
            return new OrFunction(variables.getAt(0), variables.getAt(1));
        } else if (containsSymbol(expressionString, XOR)) {
            OrderedCollection<ValueCollection<Variable>> variables = extractVariables(expressionString, XOR);
            // TODO: Refactor after the implementation of https://github.com/filipvanlaenen/kolektoj/issues/99
            ModifiableCollection<Variable> directVariables = ModifiableCollection.of(variables.getAt(0));
            directVariables.addAll(variables.getAt(1));
            // TODO: Refactor after the implementation of https://github.com/filipvanlaenen/kolektoj/issues/108
            Variable[] directArray = directVariables.toArray(new Variable[0]);
            ValueCollection<Variable> direct = ValueCollection.of(directArray);
            return new XorFunction(direct, variables.getAt(1).size() % 2 == 1);
        } else if (expressionString.startsWith(NOT.getSymbol())) {
            return new NegationExpression(new Variable(expressionString.substring(1)));
        } else {
            return new IdentityExpression(new Variable(expressionString));
        }
    }

    public static VectorialFunction parseVectorialFunction(final String vectorialFunctionString) {
        return new VectorialFunction(vectorialFunctionString.lines().map(line -> line.replaceAll("\\s", ""))
                .filter(line -> line.contains("=")).map(line -> line.split("="))
                .collect(Collectors.<String[], Variable, Expression>toMap(parts -> new Variable(parts[0]),
                        parts -> parseExpression(parts[1]))));
    }
}
