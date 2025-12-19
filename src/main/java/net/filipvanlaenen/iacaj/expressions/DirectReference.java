package net.filipvanlaenen.iacaj.expressions;

public record DirectReference(boolean negated, Operand term) implements Expression {
}
