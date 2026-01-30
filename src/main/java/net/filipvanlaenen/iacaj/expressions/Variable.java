package net.filipvanlaenen.iacaj.expressions;

public record Variable(String name) {
    @Override
    public String toString() {
        return name;
    }
}
