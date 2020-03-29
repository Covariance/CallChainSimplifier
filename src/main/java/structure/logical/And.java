package structure.logical;

import structure.AbstractBinaryOperator;

public final class And extends AbstractBinaryOperator implements LogicalExpression {
    public And(LogicalExpression left, LogicalExpression right) {
        super(left, right);
    }

    protected int calculate(int x, int y) {
        return ((x & y) == 1) ? 1 : 0;
    }

    protected String getSign() {
        return "&";
    }
}
