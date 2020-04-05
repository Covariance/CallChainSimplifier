package ru.covariance.jbintern.structure;

public interface BooleanExpression extends Expression {
    boolean evaluate(int element);
}
