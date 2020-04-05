package ru.covariance.jbintern.structure;

/**
 * Object that represents 'and' boolean expression
 */
public final class And extends LogicalExpression {
    /**
     * Constructs new And LogicalExpression with given boolean operands
     * @param left left operand
     * @param right right operand
     */
    public And(BooleanExpression left, BooleanExpression right) {
        super(left, right);
    }

    protected String getSign() {
        return "&";
    }

    /**
     * Returns true only if both operands evaluate to true
     * @param element given value
     * @return true only if both operands evaluate to true
     */
    @Override
    public boolean evaluate(int element) {
        return left.evaluate(element) && right.evaluate(element);
    }
}
