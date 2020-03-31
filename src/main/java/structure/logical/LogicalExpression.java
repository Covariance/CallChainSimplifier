package structure.logical;

import structure.Expression;

public interface LogicalExpression extends Expression {
    @Override
    default boolean isArithmetical() {
        return false;
    }
}
