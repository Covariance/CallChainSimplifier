package structure;

public abstract class LogicalExpression implements BooleanExpression {
    protected BooleanExpression left, right;

    public LogicalExpression(BooleanExpression left, BooleanExpression right) {
        this.left = left;
        this.right = right;
    }

    abstract protected String getSign();

    @Override
    public void compose(Polynomial poly) {
        left.compose(poly);
        right.compose(poly);
    }

    @Override
    public String toString() {
        return "(" + left.toString() + getSign() + right.toString() + ")";
    }
}
