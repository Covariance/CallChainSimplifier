package ru.covariance.jbintern.structure;

/**
 * Object that represents boolean expression that is operation with other boolean expressions
 */
public abstract class LogicalExpression implements BooleanExpression {
    protected BooleanExpression left, right;

    /**
     * Constructs new LogicalExpression with given boolean operands
     * @param left left operand
     * @param right right operand
     */
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
    public String toMiniString() {
        return "(" + left.toMiniString() + getSign() + right.toMiniString() + ")";
    }

    @Override
    public String toString() {
        return "(" + left.toString() + getSign() + right.toString() + ")";
    }
}
