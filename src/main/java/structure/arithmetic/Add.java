package structure.arithmetic;

import structure.AbstractBinaryOperator;

public final class Add extends AbstractBinaryOperator implements ArithmeticExpression {
    public Add(ArithmeticExpression left, ArithmeticExpression right) {
        super(left, right);
    }

    protected int calculate(int x, int y) {
        return x + y;
    }

    protected String getSign() {
        return "+";
    }
}
