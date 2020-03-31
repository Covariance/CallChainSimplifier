package structure.arithmetic;

import structure.AbstractBinaryOperator;

public final class Add extends AbstractBinaryOperator implements ArithmeticalExpression {
    public Add(ArithmeticalExpression left, ArithmeticalExpression right) {
        super(left, right);
    }

    protected int calculate(int x, int y) {
        return x + y;
    }

    protected String getSign() {
        return "+";
    }
}
