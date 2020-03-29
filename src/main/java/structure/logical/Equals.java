package structure.logical;

import structure.AbstractBinaryOperator;
import structure.Expression;
import structure.arithmetic.ArithmeticExpression;

public final class Equals extends AbstractBinaryOperator implements LogicalExpression {
    public Equals(ArithmeticExpression left, ArithmeticExpression right) {
        super(left, right);
    }

    protected int calculate(int x, int y) {
        return (x == y) ? 1 : 0;
    }

    protected String getSign() {
        return "=";
    }
}
