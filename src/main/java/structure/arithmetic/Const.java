package structure.arithmetic;

public final class Const implements ArithmeticalExpression {
    public Const(int val) {
        this.val = val;
    }

    private final int val;

    public int evaluate(int x) {
        return val;
    }

    @Override
    public String toString() {
        return Integer.toString(val);
    }
}
