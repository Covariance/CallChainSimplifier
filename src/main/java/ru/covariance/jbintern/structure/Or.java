package ru.covariance.jbintern.structure;

/**
 * Object that represents 'or' boolean expression
 */
public final class Or extends LogicalExpression {
    /**
     * Constructs new And LogicalExpression with given boolean operands
     * @param left left operand
     * @param right right operand
     */
    public Or(BooleanExpression left, BooleanExpression right) {
        super(left, right);
    }

    protected String getSign() {
        return "|";
    }

    /**
     * Returns true if at least one of operands evaluate to true
     * @param element given value
     * @return true if at least one of operands evaluate to true
     */
    @Override
    public boolean evaluate(int element) {
        return left.evaluate(element) || right.evaluate(element);
    }
}
