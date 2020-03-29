package structure.arithmetic;

public final class Variable implements ArithmeticExpression {
    public int evaluate(int x) {
        return x;
    }

    @Override
    public String toString() {
        return "element";
    }
}
