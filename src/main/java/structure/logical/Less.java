package structure.logical;

import structure.AbstractBinaryOperator;
import structure.arithmetic.ArithmeticalExpression;

public final class Less extends AbstractBinaryOperator implements LogicalExpression {
    public Less(ArithmeticalExpression left, ArithmeticalExpression right) {
        super(left, right);
    }

    protected int calculate(int x, int y) {
        return (x < y) ? 1 : 0;
    }

    protected String getSign() {
        return "<";
    }
}
