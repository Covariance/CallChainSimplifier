package ru.covariance.jbintern.structure;

public final class And extends LogicalExpression {
    public And(BooleanExpression left, BooleanExpression right) {
        super(left, right);
    }

    protected String getSign() {
        return "&";
    }

    @Override
    public boolean evaluate(int element) {
        return left.evaluate(element) && right.evaluate(element);
    }
}
