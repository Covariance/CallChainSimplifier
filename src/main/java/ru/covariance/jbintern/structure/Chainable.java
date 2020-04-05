package ru.covariance.jbintern.structure;

import java.util.List;

/**
 * Object that can be a part of ChainCall
 */
public interface Chainable {
    /**
     * Apply this operator to the given list of Integers
     * @param array given list of Integers
     * @return result of applying this operator to given list of Integers
     */
    List<Integer> apply(List<Integer> array);

    /**
     * Composes this operator with given Polynomial, changing its contents accordingly
     * @param poly Polynomial to compose with
     */
    void compose(Polynomial poly);

    /**
     * Returns minimal string representation of this operator
     * @return minimal string representation of this operator
     */
    String toMiniString();
}
