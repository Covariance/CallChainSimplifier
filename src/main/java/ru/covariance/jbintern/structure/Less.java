package ru.covariance.jbintern.structure;

/**
 * Object that represents 'less' boolean expression that compares an arithmetical expression with zero
 */
public final class Less extends ComparativeExpression {
    /**
     * Constructs new Less with given Polynomial compared to zero
     * @param poly Polynomial to compare
     */
    public Less(Polynomial poly) {
        super(poly);
    }

    protected String getSign() {
        return "<";
    }

    /**
     * Returns true only if Polynomial evaluates to negative value
     * @param element given value
     * @return true only if Polynomial evaluates to negative value
     */
    @Override
    public boolean evaluate(int element) {
        return poly.evaluate(element) < 0;
    }
}
