package ru.covariance.jbintern.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Object that represent part of call chain that retains only matching elements in provided list
 */
public final class Filter implements Chainable {
    private final BooleanExpression filter;

    /**
     * Constructs new Filter with a filter equal to given BooleanExpression
     * @param filter given BooleanExpression
     */
    public Filter(BooleanExpression filter) {
        this.filter = filter;
    }

    /**
     * Returns filtering BooleanExpression of this Filter
     * @return filtering BooleanExpression of this Filter
     */
    public BooleanExpression getFilter() {
        return filter;
    }

    /**
     * Retains elements in a resulting list only if they match filtering BooleanExpression
     * @param array given list of Integers
     * @return new Integer list, which consists only of elements of given one that match filtering BooleanExpression
     */
    @Override
    public List<Integer> apply(List<Integer> array) {
        List<Integer> result = new ArrayList<>();
        for (Integer a : array) {
            if (filter.evaluate(a)) {
                result.add(a);
            }
        }
        return result;
    }

    @Override
    public void compose(Polynomial poly) {
        filter.compose(poly);
    }

    @Override
    public String toMiniString() {
        return "filter{" + filter.toMiniString() + "}";
    }

    @Override
    public String toString() {
        return "filter{" + filter.toString() + "}";
    }
}
