package ru.covariance.jbintern.structure;

/**
 * Object that is a either logical or arithmetical expression
 */
public interface Expression {
    /**
     * Composes this expression with given Polynomial, changing its contents accordingly
     * @param poly Polynomial to compose with
     */
    void compose(Polynomial poly);
}
