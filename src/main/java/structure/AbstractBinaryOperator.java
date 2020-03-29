package structure;

public abstract class AbstractBinaryOperator implements Expression {
    protected final Expression left, right;

    public AbstractBinaryOperator(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    protected abstract int calculate(int x, int y);
    protected abstract String getSign();

    public int evaluate(int x) {
        return calculate(left.evaluate(x), right.evaluate(x));
    }

    @Override
    public String toString() {
        return "(" + left.toString() + this.getSign() + right.toString() + ")";
    }
}
