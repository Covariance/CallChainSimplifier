package ru.covariance.jbintern.structure;

/**
 * Object that represents BooleanExpression
 */
public interface BooleanExpression extends Expression {
    /**
     * Evaluate result of this expression when element equals to a given value
     * @param element given value
     * @return result of this expression when element equals to a given value
     */
    boolean evaluate(int element);

    /**
     * Returns representation of this BooleanExpression without products with ones
     * and sums with zeros
     * @return minimal representation of this BooleanExpression
     */
    String toMiniString();
}
