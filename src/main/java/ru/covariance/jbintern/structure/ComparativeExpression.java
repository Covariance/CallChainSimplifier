package ru.covariance.jbintern.structure;

/**
 * Object that represents boolean expression that compares an arithmetical expression with zero
 */
public abstract class ComparativeExpression implements BooleanExpression {
    protected Polynomial poly;

    /**
     * Constructs new ComparativeExpression with given Polynomial compared to zero
     * @param poly Polynomial to compare
     */
    public ComparativeExpression(Polynomial poly) {
        this.poly = poly;
    }

    abstract protected String getSign();

    @Override
    public void compose(Polynomial poly) {
        this.poly.compose(poly);
    }

    @Override
    public String toString() {
        return "(" + poly.toString() + getSign() + "0" + ")";
    }
}
