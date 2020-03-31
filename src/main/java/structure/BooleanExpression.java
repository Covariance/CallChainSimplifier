package structure;

public interface BooleanExpression extends Expression {
    boolean evaluate(int element);

    @Override
    default boolean isPolynomial() {
        return false;
    }
}
