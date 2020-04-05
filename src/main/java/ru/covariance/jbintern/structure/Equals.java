package ru.covariance.jbintern.structure;

/**
 * Object that represents 'equals' boolean expression that compares an arithmetical expression with zero
 */
public final class Equals extends ComparativeExpression {
    /**
     * Constructs new Equals with given Polynomial compared to zero
     * @param poly Polynomial to compare
     */
    public Equals(Polynomial poly) {
        super(poly);
    }

    protected String getSign() {
        return "=";
    }

    /**
     * Returns true only if Polynomial evaluates to zero
     * @param element given value
     * @return true only if Polynomial evaluates to zero
     */
    @Override
    public boolean evaluate(int element) {
        return poly.evaluate(element) == 0;
    }
}
