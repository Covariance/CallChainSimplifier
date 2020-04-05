package ru.covariance.jbintern.structure;

/**
 * Object that represents 'greater' boolean expression that compares an arithmetical expression with zero
 */
public final class Greater extends ComparativeExpression {
    /**
     * Constructs new Greater with given Polynomial compared to zero
     * @param poly Polynomial to compare
     */
    public Greater(Polynomial poly) {
        super(poly);
    }

    protected String getSign() {
        return ">";
    }

    /**
     * Returns true only if Polynomial evaluates to positive value
     * @param element given value
     * @return true only if Polynomial evaluates to positive value
     */
    @Override
    public boolean evaluate(int element) {
        return poly.evaluate(element) > 0;
    }
}
