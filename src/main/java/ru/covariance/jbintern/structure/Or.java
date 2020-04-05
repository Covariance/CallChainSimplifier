package structure;

public final class Or extends LogicalExpression {
    public Or(BooleanExpression left, BooleanExpression right) {
        super(left, right);
    }

    protected String getSign() {
        return "|";
    }

    @Override
    public boolean evaluate(int element) {
        return left.evaluate(element) || right.evaluate(element);
    }
}
