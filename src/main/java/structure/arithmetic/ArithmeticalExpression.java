package structure.arithmetic;

import structure.Expression;

public interface ArithmeticalExpression extends Expression {
    @Override
    default boolean isArithmetical() {
        return true;
    }
}
